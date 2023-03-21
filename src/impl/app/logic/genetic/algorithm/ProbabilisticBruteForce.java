package logic.genetic.algorithm;

import static logic.genetic.algorithm.GeneticAlgorithm.Type.PROBABILISTIC_BRUTE;

import java.util.Random;
import logic.genetic.Generator;
import logic.genetic.HallOfFame;
import logic.genetic.evaluator.Evaluator;
import models.entity.GAParameters;
import models.entity.GAResult;
import models.entity.InstanceParameters;
import models.entity.Population;
import org.slf4j.Logger;

public class ProbabilisticBruteForce extends BaseGeneticAlgorithm {

  public ProbabilisticBruteForce(
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
    for (int iter = 1; iter <= gaParams.getMaxNumberOfIter(); iter++) {
      hof.log(generateRandomPopulation(), iter).withPrintLast(logger, gaParams);
    }
    return new GAResult(hof);
  }

  private Population generateRandomPopulation() {
    return new Population(generator.generateRandomIndividualList(instanceParams.getPaintings(),
        gaParams.getPopulationSize()), evaluator, gaParams);
  }

  @Override
  public Type getType() {
    return PROBABILISTIC_BRUTE;
  }
}
