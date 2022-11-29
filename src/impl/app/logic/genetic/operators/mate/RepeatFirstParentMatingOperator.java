package logic.genetic.operators.mate;

import models.entity.Individual;

import java.util.List;

public class RepeatFirstParentMatingOperator implements MatingOperator {

  @Override
  public List<Individual> mate(Individual p1, Individual p2) {
    return List.of(p1.clone(), p1.clone());
  }

  @Override
  public Type getType() {
    return Type.REPEAT_FIRST_PARENT;
  }
}
