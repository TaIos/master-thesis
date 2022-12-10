package logic.genetic;

import logic.objective.Objective;
import lombok.Builder;
import lombok.Getter;
import models.entity.Individual;
import models.entity.InstanceParameters;

@Getter
@Builder
public class Evaluator {

  private final Placing placing;
  private final Objective objective;
  private final InstanceParameters params;

  public Evaluator(Placing placing, Objective objective, InstanceParameters params) {
    this.placing = placing;
    this.objective = objective;
    this.params = params;
  }

  public void eval(Individual ind) {
    placing.setPaintingLayout(ind, params.getLayout().getBoundingRectangle());
    Double objectiveVal = objective.eval(ind.getPaintingSeq());
    ind.setObjectiveValue(objectiveVal);
  }
}
