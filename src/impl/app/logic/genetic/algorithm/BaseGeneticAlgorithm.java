package logic.genetic.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import logic.genetic.Generator;
import logic.genetic.HallOfFame;
import logic.genetic.evaluator.Evaluator;
import lombok.Getter;
import models.entity.GAParameters;
import models.entity.Individual;
import models.entity.InstanceParameters;
import models.entity.Population;
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

  protected void mutate(List<Individual> pop) {
    pop.parallelStream()
        .forEach(
            ind -> {
              if (gaParams.getMutationProb() > rnd.nextDouble()) {
                gaParams.getMutateOperator().mutate(ind);
              }
            });
  }

  protected List<Individual> crossover(Population pop, int size) {
    List<Individual> res = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      Individual p1 = pop.getEvaluatedIndividuals().get(i % pop.size()).getIndividual();
      Individual p2 = pop.getEvaluatedIndividuals().get((i + 1) % pop.size()).getIndividual();
      res.add(gaParams.getMatingOperator().mate(p1, p2));
    }
    return res;
  }

  protected List<Individual> select(Population pop, int size) {
    return gaParams.getSelectOperator().select(pop, size);
  }

  protected Population generateInitialPopulation() {
    return new Population(generator.generateRandomIndividualList(instanceParams.getPaintings(),
        gaParams.getPopulationSize()), evaluator);
  }

}
