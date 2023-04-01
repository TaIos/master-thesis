package logic.objective;

import static logic.objective.Objective.ObjectiveType.SIMPLE;

import java.util.List;
import java.util.stream.Collectors;
import logic.metric.EuclideanMetric;
import logic.objective.parts.IsOutsideOfAllocatedArea;
import logic.objective.parts.OverlappingRectanglesCount;
import logic.objective.parts.PaintingFlowSum;
import models.entity.EvaluatedSlicingLayout;
import models.entity.PaintingPlacement;
import models.entity.PaintingsFlow;
import models.entity.PlacedSlicingLayout;
import models.entity.SimpleObjectiveParameters;
import utils.FunctionThreadSafeWrapper;
import utils.FunctionThreadSafeWrapper.FunctionBorrow;

public class SimpleObjective implements Objective {

  private final SimpleObjectiveParameters params;
  private final FunctionThreadSafeWrapper functionWrapper;

  private final EuclideanMetric euclideanMetric;

  // parts
  private final IsOutsideOfAllocatedArea isOutsideOfAllocatedArea;
  private final OverlappingRectanglesCount overlappingRectanglesCount;
  private final PaintingFlowSum paintingFlowSum;

  public SimpleObjective(SimpleObjectiveParameters params,
      FunctionThreadSafeWrapper functionWrapper,
      EuclideanMetric euclideanMetric, IsOutsideOfAllocatedArea isOutsideOfAllocatedArea,
      OverlappingRectanglesCount overlappingRectanglesCount, PaintingFlowSum paintingFlowSum) {
    this.params = params;
    this.functionWrapper = functionWrapper;
    this.euclideanMetric = euclideanMetric;
    this.isOutsideOfAllocatedArea = isOutsideOfAllocatedArea;
    this.overlappingRectanglesCount = overlappingRectanglesCount;
    this.paintingFlowSum = paintingFlowSum;
  }

  @Override
  public EvaluatedSlicingLayout eval(PlacedSlicingLayout layout, PaintingsFlow flow) {
    return EvaluatedSlicingLayout.builder()
        .placements(layout.getPlacements())
        .objectiveValue(computeObjective(layout.getPlacements(), flow))
        .build();
  }

  @Override
  public double peek(PlacedSlicingLayout layout, PaintingPlacement placement, PaintingsFlow flow) {
    return functionValueSum(List.of(placement))
        + outsideAllocatedAreaSum(List.of(placement))
        + overlappingOne(layout, placement)
        + flowSumOne(layout, placement, flow);
  }

  private double computeObjective(List<PaintingPlacement> placements, PaintingsFlow flow) {
    return functionValueSum(placements)
        + outsideAllocatedAreaSum(placements)
        + overlapping(placements)
        + flowSum(placements, flow);
  }

  private double functionValueSum(List<PaintingPlacement> placements) {
    return placements.parallelStream()
        .map(this::functionValue)
        .mapToDouble(Double::doubleValue)
        .sum();
  }

  private double functionValue(PaintingPlacement placement) {
    try (FunctionBorrow fb = functionWrapper.getNext()) {
      return fb.getFunction().calculate(
          placement.getPlacement().getX(),
          placement.getPlacement().getY());
    }
  }

  private double outsideAllocatedAreaSum(List<PaintingPlacement> placements) {
    return placements.parallelStream()
        .map(this::outsideAllocatedArea)
        .mapToDouble(Double::doubleValue)
        .sum();
  }

  private double outsideAllocatedArea(PaintingPlacement placement) {
    return isOutsideOfAllocatedArea.calculate(placement)
        ? params.getOutsideOfAllocatedAreaPenalizationConstant()
        : 0;
  }

  private double overlapping(List<PaintingPlacement> placements) {
    return overlappingRectanglesCount.calculate(
        placements.stream()
            .map(PaintingPlacement::getPlacement)
            .collect(Collectors.toList())) * params.getOverlappingPenalizationConstant();
  }

  private double overlappingOne(PlacedSlicingLayout layout, PaintingPlacement placement) {
    return overlappingRectanglesCount.calculateOne(
        placement.getPlacement(),
        layout.getPlacements().stream()
            .map(PaintingPlacement::getPlacement)
            .collect(Collectors.toList())
    ) * params.getOverlappingPenalizationConstant();
  }

  private double flowSum(List<PaintingPlacement> placements, PaintingsFlow flow) {
    return paintingFlowSum.calculate(placements, flow, euclideanMetric);
  }

  private double flowSumOne(PlacedSlicingLayout layout, PaintingPlacement placement,
      PaintingsFlow flow) {
    return paintingFlowSum.calculateForOne(placement, layout.getPlacements(), flow,
        euclideanMetric);
  }

  @Override
  public ObjectiveType getType() {
    return SIMPLE;
  }
}
