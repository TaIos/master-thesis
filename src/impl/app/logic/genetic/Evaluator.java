package logic.genetic;

import static logic.objective.Objective.OBJECTIVE_VALUE_MAX;

import factory.copy_factory.PointCopyFactory;
import factory.copy_factory.RectangleCopyFactory;
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

  private final RectangleCopyFactory rectangleCopyFactory;
  private final PointCopyFactory pointCopyFactory;

  public Evaluator(PaintingSpaceAllocator placing,
      Objective objective,
      InstanceParameters params,
      OrientationResolver orientationResolver,
      RectangleCopyFactory rectangleCopyFactory,
      PointCopyFactory pointCopyFactory) {
    this.placing = placing;
    this.objective = objective;
    this.params = params;
    this.orientationResolver = orientationResolver;
    this.rectangleCopyFactory = rectangleCopyFactory;
    this.pointCopyFactory = pointCopyFactory;
  }

  public void eval(Individual ind) {
    EvaluationParameters bestParams = new EvaluationParameters().withMaxObjectiveValue();

    for (var resolved : orientationResolver.resolve(ind.getOrientations())) {
      placing.computeAndSetPaintingAllocatedSpace(ind, resolved,
          params.getLayout().getBoundingRectangle());
      double objectiveVal = objective.eval(ind.getPaintingSeq());
      bestParams.updateIfBetter(objectiveVal, resolved, ind.getPaintingSeq());
    }

    bestParams.setIndividualParameters(ind);
  }

  private class EvaluationParameters {

    private double objectiveVal;
    private List<Orientation> orientationsResolved;
    private List<Rectangle> allocatedSpace;
    private List<Point> placements;

    public EvaluationParameters withMaxObjectiveValue() {
      objectiveVal = OBJECTIVE_VALUE_MAX;
      return this;
    }

    public void updateIfBetter(Double objectiveVal, List<Orientation> orientationsResolved,
        List<Painting> paintingSeqToCopy) {
      if (objectiveVal < this.objectiveVal) {
        this.objectiveVal = objectiveVal;
        this.orientationsResolved = orientationsResolved;
        this.allocatedSpace = paintingSeqToCopy.stream().map(Painting::getAllocatedSpace)
            .map(rectangleCopyFactory::createCopy).collect(Collectors.toList());
        this.placements = paintingSeqToCopy.stream().map(Painting::getPlacement)
            .map(pointCopyFactory::createCopy).collect(Collectors.toList());
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
