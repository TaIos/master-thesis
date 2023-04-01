package logic.genetic.operators.mate;

import models.entity.Individual;

public interface BiasedMatingOperator extends MatingOperator {

  Individual mate(Individual p1, Individual p2, double w1, double w2);

  @Override
  default Individual mate(Individual p1, Individual p2) {
    return mate(p1, p2, 1, 1);
  }
}
