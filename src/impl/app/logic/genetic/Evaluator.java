package logic.genetic;

import java.util.List;
import java.util.stream.Collectors;
import logic.objective.Objective;
import lombok.Builder;
import lombok.Getter;
import models.entity.Individual;
import models.entity.InstanceParameters;
import models.entity.Orientation;
import models.entity.Painting;
import models.entity.Rectangle;

@Getter
@Builder
public class Evaluator {

  private final Placing placing;
  private final Objective objective;
  private final InstanceParameters params;
  private final OrientationResolver orientationResolver;

  public Evaluator(Placing placing, Objective objective, InstanceParameters params,
      OrientationResolver orientationResolver) {
    this.placing = placing;
    this.objective = objective;
    this.params = params;
    this.orientationResolver = orientationResolver;
  }

  public void eval(Individual ind) {
    double bestObjectiveVal = Individual.OBJECTIVE_VALUE_MAX;
    List<Orientation> bestOrientationResolved = null;
    List<Rectangle> bestPlacement = null;
    var orientationsUnresolved = ind.getOrientations();

    for (var orientationResolved : orientationResolver.resolve(orientationsUnresolved)) {
      ind.setOrientations(orientationResolved);
      placing.setPaintingLayout(ind, params.getLayout().getBoundingRectangle());
      double objectiveVal = objective.eval(ind.getPaintingSeq());
      if (objectiveVal < bestObjectiveVal) {
        bestObjectiveVal = objectiveVal;
        bestOrientationResolved = orientationResolved;
        bestPlacement = ind.getPaintingSeq().stream().map(Painting::getPlacement)
            .map(Rectangle::clone).collect(
                Collectors.toList());
      }
    }

    ind.setOrientations(orientationsUnresolved);
    ind.setOrientationsResolved(bestOrientationResolved);
    ind.setObjectiveValue(bestObjectiveVal);
    for (int i = 0; i < ind.getPaintingSeq().size(); i++) {
      ind.getPaintingSeq().get(i).setPlacement(bestPlacement.get(i));
    }
  }
}
