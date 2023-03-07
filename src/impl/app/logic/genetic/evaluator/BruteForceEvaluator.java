package logic.genetic.evaluator;

import static logic.genetic.evaluator.Evaluator.Type.BRUTE;

import java.util.List;
import javax.annotation.Nullable;
import logic.objective.Objective;
import logic.objective.ObjectiveValueComparator;
import models.entity.EvaluatedSlicingLayout;
import models.entity.Individual;
import models.entity.InstanceParameters;
import models.entity.PaintingPlacement;
import models.entity.Rectangle;

public class BruteForceEvaluator implements Evaluator {

  private final Objective objective;
  private final ObjectiveValueComparator objectiveValueComparator;

  private final InstanceParameters params;

  public BruteForceEvaluator(Objective objective, ObjectiveValueComparator objectiveValueComparator,
      InstanceParameters params) {
    this.objective = objective;
    this.objectiveValueComparator = objectiveValueComparator;
    this.params = params;
  }

  @Override
  public EvaluatedSlicingLayout eval(@Nullable Individual ind) {
    // TODO
    return EvaluatedSlicingLayout.builder()
        .placements(List.of(
            PaintingPlacement.builder()
                .allocatedSpace(Rectangle.builder().x(1).y(1).width(1).height(1).build())
                .placement(Rectangle.builder().x(1).y(1).width(1).height(1).build())
                .build())
        )
        .objectiveValue(-1d)
        .build();
  }

  @Override
  public ObjectiveValueComparator getObjectiveValueComparator() {
    return objectiveValueComparator;
  }

  @Override
  public Type getType() {
    return BRUTE;
  }
}
