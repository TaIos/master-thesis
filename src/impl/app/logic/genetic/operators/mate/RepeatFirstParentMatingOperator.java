package logic.genetic.operators.mate;

import models.entity.Individual;

public class RepeatFirstParentMatingOperator implements MatingOperator {

  @Override
  public Individual mate(Individual p1, Individual p2) {
    return p1.clone();
  }

  @Override
  public Type getType() {
    return Type.REPEAT_FIRST_PARENT;
  }
}
