package logic.genetic.operators.mutate;

import static utils.JavaUtils.Vector.normalizeToProbabilityVectorInplace;

import java.util.List;
import java.util.Random;

public abstract class BaseFlipOperator implements MutateOperator {

  protected final Random rnd;

  protected BaseFlipOperator(Random rnd) {
    this.rnd = rnd;
  }

  protected void fliOneAtRandomAndNormalizeInplace(List<Double> lst) {
    flipOneAtRandomInplace(lst);
    normalizeToProbabilityVectorInplace(lst);
  }

  protected void flipOneAtRandomInplace(List<Double> lst) {
    int idx = rnd.nextInt(lst.size());
    lst.set(idx, rnd.nextDouble());
  }
}
