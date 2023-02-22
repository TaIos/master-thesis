package logic.genetic.algorithm;

import factory.BestIndividualFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

public class SimpleGA extends BaseGeneticAlgorithm {

  private final BestIndividualFactory bestIndividualFactory;

  public SimpleGA(
      GAParameters gaParams,
      InstanceParameters instanceParams,
      Evaluator evaluator,
      HallOfFame hof,
      Generator generator,
      Random rnd,
      Logger logger,
      BestIndividualFactory bestIndividualFactory) {
    super(gaParams, instanceParams, evaluator, hof, generator, rnd, logger);
    this.bestIndividualFactory = bestIndividualFactory;
  }

  @Override
  public GAResult call() {
    Population pop = generateInitialPopulation();
    BestIndividual bestInd = bestIndividualFactory.create(pop);
    hof.log(pop, 0);
    printIterationStats(0, pop);

    for (int iter = 1, popSize = gaParams.getPopulationSize();
        iter <= gaParams.getMaxNumberOfIter();
        iter++) {
      List<Individual> popNext = new ArrayList<>(popSize);
      int s = select(pop, popNext, popSize / 2);
      crossover(pop, popNext, popSize - s);
      mutate(popNext);

      pop = new Population(popNext, evaluator);
      bestInd = bestIndividualFactory.createUpdated(bestInd, pop, iter);
      printIterationStats(iter, pop);
      hof.log(pop, iter);
    }

    return new GAResult(bestInd, hof);
  }

  @Override
  public Type getType() {
    return Type.SIMPLE_GA;
  }
}
