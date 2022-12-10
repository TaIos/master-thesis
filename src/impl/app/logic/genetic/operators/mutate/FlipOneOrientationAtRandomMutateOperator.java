package logic.genetic.operators.mutate;

import java.util.Random;
import models.entity.Individual;

public class FlipOneOrientationAtRandomMutateOperator implements MutateOperator {

  private final Random rnd;

  public FlipOneOrientationAtRandomMutateOperator(Random random) {
    rnd = random;
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
