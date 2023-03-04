package logic.genetic.operators.select;

import java.util.List;
import java.util.stream.Collectors;
import models.entity.EvaluatedIndividual;
import models.entity.Individual;
import models.entity.Population;

public class SelectBestOperator implements SelectOperator {

  @Override
  public List<Individual> select(Population population, int size) {
    return population.getEvaluatedIndividuals()
        .subList(0, Math.min(size, population.size()))
        .stream().map(EvaluatedIndividual::getIndividual).collect(Collectors.toList());
  }

  @Override
  public Type getType() {
    return Type.BEST;
  }
}
