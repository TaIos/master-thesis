package logic.genetic.algorithm;

import java.util.Random;
import logic.genetic.Generator;
import logic.genetic.HallOfFame;
import logic.genetic.evaluator.Evaluator;
import lombok.Getter;
import models.entity.GAParameters;
import models.entity.InstanceParameters;
import org.slf4j.Logger;

@Getter
public abstract class BaseGeneticAlgorithm implements GeneticAlgorithm {

  protected final GAParameters gaParams;
  protected final InstanceParameters instanceParams;
  protected final Evaluator evaluator;
  protected final HallOfFame hof;

  protected final Generator generator;
  protected final Random rnd;
  protected final Logger logger;

  public BaseGeneticAlgorithm(
      GAParameters gaParams,
      InstanceParameters instanceParams,
      Evaluator evaluator,
      HallOfFame hof,
      Generator generator,
      Random rnd,
      Logger logger) {
    this.gaParams = gaParams;
    this.instanceParams = instanceParams;
    this.evaluator = evaluator;
    this.hof = hof;
    this.generator = generator;
    this.rnd = rnd;
    this.logger = logger;
  }


}
