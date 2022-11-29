package logic.genetic.operators.mutate;

import models.entity.Individual;

import java.util.Random;

import static logic.genetic.operators.mutate.MutateOperator.Type.FLIP_ONES_SLICING_ORDER_AT_RANDOM;

public class FlipOneSlicingOrderAtRandom implements MutateOperator {

  private final Random rnd;

  public FlipOneSlicingOrderAtRandom(Random rnd) {
    this.rnd = rnd;
  }

  @Override
  public void mutate(Individual ind) {
    int idx = rnd.nextInt(ind.getSlicingOrderRandomKey().size());
    ind.getSlicingOrderRandomKey().set(idx, rnd.nextDouble());
  }

  @Override
  public Type getType() {
    return FLIP_ONES_SLICING_ORDER_AT_RANDOM;
  }
}
