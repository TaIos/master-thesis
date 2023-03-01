package logic.genetic.operators.mutate;

import static logic.genetic.operators.mutate.MutateOperator.Type.FLIP_SLICING_ORDER;

import java.util.Random;
import models.entity.Individual;

public class FlipSlicingOrder extends BaseFlipOperator {

  public FlipSlicingOrder(Random rnd) {
    super(rnd);
  }

  @Override
  public void mutate(Individual ind) {
    fliOneAtRandomAndNormalizeInplace(ind.getSlicingOrderRandomKey());
  }

  @Override
  public Type getType() {
    return FLIP_SLICING_ORDER;
  }
}
