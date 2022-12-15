package logic.objective;

import static logic.objective.Objective.Type.SIMPLE;
import static utils.JavaUtils.toInteger;

import java.util.List;
import logic.objective.parts.global.CalculateOverlappingPaintings;
import logic.objective.parts.local.IsOutsideOfAllocatedArea;
import logic.placing.PaintingLocalPlacer;
import models.entity.Painting;
import models.entity.SimpleObjectiveParameters;
import utils.FunctionThreadSafeWrapper;
import utils.FunctionThreadSafeWrapper.FunctionBorrow;

public class SimpleObjective implements Objective {

  private final FunctionThreadSafeWrapper functionWrapper;
  private final PaintingLocalPlacer localPlacer;

  private final SimpleObjectiveParameters params;

  // parts - local
  private final IsOutsideOfAllocatedArea isOutsideOfAllocatedArea;

  // parts - global
  private final CalculateOverlappingPaintings overlappingPaintings;

  public SimpleObjective(FunctionThreadSafeWrapper functionWrapper, PaintingLocalPlacer localPlacer,
      SimpleObjectiveParameters params, IsOutsideOfAllocatedArea isOutsideOfAllocatedArea,
      CalculateOverlappingPaintings overlappingPaintings) {
    this.functionWrapper = functionWrapper;
    this.localPlacer = localPlacer;
    this.params = params;
    this.isOutsideOfAllocatedArea = isOutsideOfAllocatedArea;
    this.overlappingPaintings = overlappingPaintings;
  }

  @Override
  public double eval(List<Painting> paintingList) {
    // TODO make evaluation order independent
    return paintingList.parallelStream().mapToDouble(this::evalLocal).sum()
        + evalGlobal(paintingList);
  }

  // TODO outsource constant to input params
  private double evalGlobal(List<Painting> paintingList) {
    return params.getOverlappingPenalizationConstant() * overlappingPaintings.eval(paintingList);
  }

  private double evalLocal(Painting painting) {
    try (FunctionBorrow fb = functionWrapper.getNext()) {
      return localPlacer.placeAndEval(painting, fb.getFunction())
          + params.getOutsideOfAllocatedAreaPenalizationConstant() * toInteger(
          isOutsideOfAllocatedArea.eval(
              painting));
    }
  }

  @Override
  public Type getType() {
    return SIMPLE;
  }
}
