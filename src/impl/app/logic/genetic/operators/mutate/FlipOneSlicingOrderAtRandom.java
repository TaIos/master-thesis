package logic.genetic.operators.mutate;

import static logic.genetic.operators.mutate.MutateOperator.Type.FLIP_ONES_SLICING_ORDER_AT_RANDOM;

import java.util.Random;
import models.entity.Individual;

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
