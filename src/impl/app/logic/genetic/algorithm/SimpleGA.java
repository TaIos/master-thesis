package logic.genetic.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import logic.genetic.Evaluator;
import logic.genetic.Generator;
import logic.genetic.HallOfFame;
import models.entity.GAParameters;
import models.entity.GAResult;
import models.entity.Individual;
import models.entity.InstanceParameters;
import models.entity.Population;
import org.slf4j.Logger;

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
    hof.log(pop, 0).withPrintLast(logger, gaParams);

    for (int iter = 1, popSize = gaParams.getPopulationSize();
        iter <= gaParams.getMaxNumberOfIter();
        iter++) {

      int selectSize = pop.size() / 3;
      int crossoverSize = popSize - selectSize;
      List<Individual> popNext = new ArrayList<>(selectSize + crossoverSize);

      popNext.addAll(select(pop, selectSize));
      popNext.addAll(crossover(pop, crossoverSize));
      mutate(popNext);

      pop = new Population(popNext, evaluator);
      hof.log(pop, iter).withPrintLast(logger, gaParams);
    }

    return new GAResult(hof);
  }

  @Override
  public Type getType() {
    return Type.SIMPLE_GA;
  }
}
