package logic.genetic;

import java.util.List;
import java.util.stream.Collectors;
import logic.objective.Objective;
import logic.placing.PaintingSpaceAllocator;
import lombok.Builder;
import lombok.Getter;
import models.entity.Individual;
import models.entity.InstanceParameters;
import models.entity.Orientation;
import models.entity.Painting;
import models.entity.Point;
import models.entity.Rectangle;

@Getter
@Builder
public class Evaluator {

  private final PaintingSpaceAllocator placing;
  private final Objective objective;
  private final InstanceParameters params;
  private final OrientationResolver orientationResolver;

  public Evaluator(PaintingSpaceAllocator placing, Objective objective, InstanceParameters params,
      OrientationResolver orientationResolver) {
    this.placing = placing;
    this.objective = objective;
    this.params = params;
    this.orientationResolver = orientationResolver;
  }

  public void eval(Individual ind) {
    EvaluationParameters bestParams = EvaluationParameters.withMaxObjectiveValue();

    for (var resolved : orientationResolver.resolve(ind.getOrientations())) {
      placing.computeAndSetPaintingAllocatedSpace(ind, resolved,
          params.getLayout().getBoundingRectangle());
      double objectiveVal = objective.eval(ind.getPaintingSeq());
      bestParams.updateIfBetter(objectiveVal, resolved, ind.getPaintingSeq());
    }

    bestParams.setIndividualParameters(ind);
  }

  private static class EvaluationParameters {

    private double objectiveVal;
    private List<Orientation> orientationsResolved;
    private List<Rectangle> allocatedSpace;
    private List<Point> placements;

    public static EvaluationParameters withMaxObjectiveValue() {
      EvaluationParameters params = new EvaluationParameters();
      params.objectiveVal = Individual.OBJECTIVE_VALUE_MAX;
      return params;
    }

    public void updateIfBetter(Double objectiveVal, List<Orientation> orientationsResolved,
        List<Painting> paintingSeqToClone) {
      if (objectiveVal < this.objectiveVal) {
        this.objectiveVal = objectiveVal;
        this.orientationsResolved = orientationsResolved;
        this.allocatedSpace = paintingSeqToClone.stream().map(Painting::getAllocatedSpace)
            .map(Rectangle::clone).collect(Collectors.toList());
        this.placements = paintingSeqToClone.stream().map(Painting::getPlacement)
            .map(Point::clone).collect(Collectors.toList());
      }
    }

    public void setIndividualParameters(Individual ind) {
      ind.setOrientationsResolved(orientationsResolved);
      ind.setObjectiveValue(objectiveVal);
      for (int i = 0; i < ind.getPaintingSeq().size(); i++) {
        ind.getPaintingSeq().get(i).setAllocatedSpace(allocatedSpace.get(i));
        ind.getPaintingSeq().get(i).setPlacement(placements.get(i));
      }
    }
  }

}
