package logic.genetic.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import logic.genetic.Evaluator;
import logic.genetic.Generator;
import logic.genetic.HallOfFame;
import lombok.Getter;
import models.entity.GAParameters;
import models.entity.Individual;
import models.entity.InstanceParameters;
import models.entity.Population;
import models.entity.RandomIndividualGenerationRequest;
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

  protected void crossover(Population pop, List<Individual> popNext, int k) {
    List<Individual> machoList = pop.getIndividualList().subList(0, (int) (0.1 * pop.size()));
      if (machoList.isEmpty()) {
          return;
      }
    for (int i = 0; i < k; i += 2) {
      Individual macho = machoList.get(rnd.nextInt(machoList.size()));
      Individual rndInd = pop.getIndividualList().get(rnd.nextInt(pop.size()));
      for (var off : gaParams.getMatingOperator().mate(macho, rndInd)) {
          if (popNext.size() >= k) {
              break;
          }
        popNext.add(off);
      }
    }
  }

  protected int select(Population pop, List<Individual> popNext, int k) {
    popNext.addAll(gaParams.getSelectOperator().select(pop.getIndividualList(), k));
    return k;
  }

  protected Population generateInitialPopulation() {
    var req = new RandomIndividualGenerationRequest(instanceParams.getPaintings());
    List<Individual> pop = new ArrayList<>(gaParams.getPopulationSize());
    for (int i = 0; i < gaParams.getPopulationSize(); i++) {
      pop.add(generator.random(req));
    }
    return new Population(pop, evaluator);
  }
}
