package logic.objective;

import static logic.objective.Objective.ObjectiveType.SIMPLE;

import java.util.List;
import java.util.stream.Collectors;
import logic.objective.parts.IsOutsideOfAllocatedArea;
import logic.objective.parts.OverlappingRectanglesCount;
import models.entity.EvaluatedSlicingLayout;
import models.entity.PaintingPlacement;
import models.entity.PlacedSlicingLayout;
import models.entity.SimpleObjectiveParameters;
import utils.FunctionThreadSafeWrapper;
import utils.FunctionThreadSafeWrapper.FunctionBorrow;

public class SimpleObjective implements Objective {

  private final SimpleObjectiveParameters params;
  private final FunctionThreadSafeWrapper functionWrapper;

  private final IsOutsideOfAllocatedArea isOutsideOfAllocatedArea;
  private final OverlappingRectanglesCount overlappingRectanglesCount;

  public SimpleObjective(SimpleObjectiveParameters params,
      FunctionThreadSafeWrapper functionWrapper,
      IsOutsideOfAllocatedArea isOutsideOfAllocatedArea,
      OverlappingRectanglesCount overlappingRectanglesCount) {
    this.params = params;
    this.functionWrapper = functionWrapper;
    this.isOutsideOfAllocatedArea = isOutsideOfAllocatedArea;
    this.overlappingRectanglesCount = overlappingRectanglesCount;
  }

  @Override
  public EvaluatedSlicingLayout eval(PlacedSlicingLayout layout) {
    return EvaluatedSlicingLayout.builder()
        .placements(layout.getPlacements())
        .objectiveValue(computeObjective(layout.getPlacements()))
        .build();
  }

  @Override
  public double eval(PaintingPlacement paintingPlacement) {
    return functionValue(paintingPlacement) + outsideAllocatedArea(paintingPlacement);
  }


  private double computeObjective(List<PaintingPlacement> placements) {
    return functionValueSum(placements)
        + outsideAllocatedAreaSum(placements)
        + overlapping(placements);
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


  @Override
  public ObjectiveType getType() {
    return SIMPLE;
  }
}
