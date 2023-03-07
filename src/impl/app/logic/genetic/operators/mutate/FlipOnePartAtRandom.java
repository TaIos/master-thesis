package logic.genetic.operators.mutate;

import static logic.genetic.operators.mutate.MutateOperator.Type.FLIP_ONE_PART_AT_RANDOM;

import java.util.List;
import java.util.Random;
import models.entity.Individual;

public class FlipOnePartAtRandom implements MutateOperator {

  private final Random rnd;
  private final List<MutateOperator> flipOperators;

  public FlipOnePartAtRandom(Random rnd,
      FlipOrientationProbability flipOrientationProbability,
      FlipPaintingSequence flipPaintingSequence,
      FlipSlicingOrder flipSlicingOrder) {
    this.rnd = rnd;
    flipOperators = List.of(flipOrientationProbability, flipPaintingSequence, flipSlicingOrder);
  }


  @Override
  public void mutate(Individual ind) {
    flipOperators.get(rnd.nextInt(flipOperators.size())).mutate(ind);
  }

  @Override
  public Type getType() {
    return FLIP_ONE_PART_AT_RANDOM;
  }
}
