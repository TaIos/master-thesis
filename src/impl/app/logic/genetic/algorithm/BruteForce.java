package logic.genetic.algorithm;

import java.util.Random;
import logic.genetic.Generator;
import logic.genetic.HallOfFame;
import logic.genetic.evaluator.Evaluator;
import models.entity.GAParameters;
import models.entity.GAResult;
import models.entity.InstanceParameters;
import models.entity.Population;
import org.slf4j.Logger;

public class BruteForce extends BaseGeneticAlgorithm {


  public BruteForce(
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
    return new GAResult(hof.log(generateInitialPopulation(), 0).withPrintLast(logger, gaParams));
  }

  @Override
  protected Population generateInitialPopulation() {
    return new Population(generator.generateRandomIndividualList(instanceParams.getPaintings(),
        1), evaluator);
  }

  @Override
  public Type getType() {
    return Type.SIMPLE_GA;
  }
}
