package logic.genetic.algorithm;

import logic.genetic.Evaluator;
import logic.genetic.Generator;
import logic.genetic.HallOfFame;
import lombok.Getter;
import models.entity.GAParameters;

import java.util.Random;

@Getter
public abstract class BaseGeneticAlgorithm implements GeneticAlgorithm {

  protected final GAParameters params;
  protected final Evaluator evaluator;
  protected final HallOfFame hof;

  protected final Generator generator = new Generator();
  protected final Random rnd = new Random(System.currentTimeMillis());

  public BaseGeneticAlgorithm(GAParameters params, Evaluator evaluator, HallOfFame hof) {
    this.params = params;
    this.evaluator = evaluator;
    this.hof = hof;
  }
}
