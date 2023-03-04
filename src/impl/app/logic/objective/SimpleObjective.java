package logic.objective;

import static logic.objective.Objective.ObjectiveType.SIMPLE;

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
        .objectiveValue(computeObjective(layout))
        .build();
  }

  @Override
  public ObjectiveType getType() {
    return SIMPLE;
  }

  private double computeObjective(PlacedSlicingLayout layout) {
    return functionSum(layout) + overlapping(layout) + outsideAllocatedArea(layout);
  }

  private double functionSum(PlacedSlicingLayout layout) {
    return layout.getPlacements().parallelStream()
        .map(PaintingPlacement::getPlacement)
        .mapToDouble(rec -> {
          try (FunctionBorrow fb = functionWrapper.getNext()) {
            return fb.getFunction().calculate(rec.getX(), rec.getY());
          }
        }).sum();
  }


  private double overlapping(PlacedSlicingLayout layout) {
    return overlappingRectanglesCount.calculate(
        layout.getPlacements().stream().map(PaintingPlacement::getPlacement)
            .collect(Collectors.toList())
    ) * params.getOverlappingPenalizationConstant();
  }


  private double outsideAllocatedArea(PlacedSlicingLayout layout) {
    int cnt = 0;
    for (int i = 0; i < layout.getPlacements().size(); i++) {
      PaintingPlacement p = layout.getPlacements().get(i);
      if (isOutsideOfAllocatedArea.calculate(p.getPlacement(), p.getAllocatedSpace())) {
        cnt += 1;
      }
    }
    return cnt * params.getOutsideOfAllocatedAreaPenalizationConstant();
  }
}
