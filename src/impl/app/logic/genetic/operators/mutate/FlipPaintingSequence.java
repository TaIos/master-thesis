package logic.genetic.operators.mutate;

import static logic.genetic.operators.mutate.MutateOperator.Type.FLIP_PAINTING_SEQUENCE;

import java.util.Random;
import models.entity.Individual;

public class FlipPaintingSequence extends BaseFlipOperator {

  public FlipPaintingSequence(Random rnd) {
    super(rnd);
  }

  @Override
  public void mutate(Individual ind) {
    fliOneAtRandomAndNormalizeInplace(ind.getSlicingOrderRandomKey());
  }

  @Override
  public Type getType() {
    return FLIP_PAINTING_SEQUENCE;
  }
}
