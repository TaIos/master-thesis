package logic.genetic.operators.select;

import java.util.List;
import logic.genetic.evaluator.Evaluator;
import models.entity.Individual;

public class SelectBestOperator implements SelectOperator {

  @Override
  public List<Individual> select(List<Individual> population, int size, Evaluator evaluator) {
    assert (population.size() >= size);
    return population.subList(0, size);
  }

  @Override
  public Type getType() {
    return Type.BEST;
  }
}
