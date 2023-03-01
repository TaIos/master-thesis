package logic.genetic.operators.mutate;

import static logic.genetic.operators.mutate.MutateOperator.Type.FLIP_ORIENTATION;

import java.util.Random;
import models.entity.Individual;
import models.entity.Orientation;
import models.entity.OrientationProbability;

public class FlipOrientationProbability extends BaseFlipOperator {

  public FlipOrientationProbability(Random rnd) {
    super(rnd);
  }

  @Override
  public void mutate(Individual ind) {
    int idx = rnd.nextInt(ind.getOrientationProb().size());
    OrientationProbability prob = ind.getOrientationProb().get(idx);
    fliOneAtRandomAndNormalizeInplace(prob.getProbabilityVector());
    // TODO fix or outsource
    ind.getOrientations().set(idx, new Orientation(prob.getMostProbable()));
  }

  @Override
  public Type getType() {
    return FLIP_ORIENTATION;
  }
}
