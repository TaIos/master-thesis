package logic.genetic.algorithm;

import logic.genetic.Evaluator;
import logic.genetic.Generator;
import logic.genetic.HallOfFame;
import models.entity.BestIndividual;
import models.entity.GAParameters;
import models.entity.GAResult;
import models.entity.Individual;
import models.entity.InstanceParameters;
import models.entity.Population;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleGA extends BaseGeneticAlgorithm {

  public SimpleGA(
      GAParameters gaParams,
      InstanceParameters instanceParams,
      Evaluator evaluator,
      HallOfFame hof,
      Generator generator,
      Random rnd,
      Logger logger) {
    super(gaParams, instanceParams, evaluator, hof, generator, rnd, logger);
  }

  @Override
  public GAResult call() {
    Population pop = generateInitialPopulation();
    BestIndividual bestInd = new BestIndividual(pop, 0);
    hof.log(pop, 0);

    for (int iter = 1, popSize = gaParams.getPopulationSize();
        iter <= gaParams.getMaxNumberOfIter();
        iter++) {
      List<Individual> popNext = new ArrayList<>(popSize);
      int s = select(pop, popNext, popSize / 2);
      crossover(pop, popNext, popSize - s);
      mutate(popNext);

      pop = new Population(popNext, evaluator);
      bestInd.update(pop, iter);
      hof.log(pop, iter);
    }
    return GAResult.builder().bestIndividual(bestInd).hallOfFame(hof).build();
  }

  @Override
  public Type getType() {
    return Type.SIMPLE_GA;
  }
}
