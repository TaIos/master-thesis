package logic.genetic.operators.mutate;

import models.entity.Individual;

import java.util.Random;

public class FlipOneOrientationAtRandomMutateOperator implements MutateOperator {
  private final Random rnd;

  public FlipOneOrientationAtRandomMutateOperator() {
    rnd = new Random(System.currentTimeMillis());
  }

  @Override
  public void mutate(Individual ind) {
    ind.getOrientations().get(rnd.nextInt(ind.getOrientations().size())).flip();
  }

  @Override
  public Type getType() {
    return Type.FLIP_ONE_ORIENTATION_AT_RANDOM;
  }
}
