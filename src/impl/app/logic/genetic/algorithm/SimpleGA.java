package logic.genetic.algorithm;

import logic.genetic.Evaluator;
import logic.genetic.Generator;
import logic.genetic.HallOfFame;
import models.entity.GAParameters;
import models.entity.GAResult;
import models.entity.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimpleGA extends BaseGeneticAlgorithm {

  public SimpleGA(
      GAParameters params,
      Evaluator evaluator,
      HallOfFame hof,
      Generator generator,
      Random random) {
    super(params, evaluator, hof, generator, random);
  }

  @Override
  public GAResult call() {
    // TODO
    return GAResult.builder().test("abc").build();
    /*
    List<Individual> pop = generateInitialPopulation();
    evalAndSort(pop);
    hof.log(pop, 0);

    for (int iter = 1, popSize = params.getPopulationSize();
        iter < params.getMaxNumberOfIter();
        iter++) {
      List<Individual> popNext = new ArrayList<>(popSize);
      int s = select(pop, popNext, popSize / 2);
      crossover(pop, popNext, popSize - s);
      mutate(popNext);

      pop = popNext;
      evalAndSort(pop);
      hof.log(pop, iter);
    }
    return null;
     */
  }

  private void mutate(List<Individual> pop) {
    for (Individual ind : pop) {
      if (params.getMutationProb() > rnd.nextDouble()) {
        params.getMutateOperator().mutate(ind);
      }
    }
  }

  private void crossover(List<Individual> pop, List<Individual> popNext, int k) {
    List<Individual> machoList = pop.subList(0, (int) (0.1 * pop.size()));
    for (int i = 0; i < k; i++) {
      Individual macho = machoList.get(rnd.nextInt(machoList.size()));
      Individual randomIndividual = pop.get(rnd.nextInt(pop.size()));
      popNext.add(params.getMatingOperator().mate(macho, randomIndividual));
    }
  }

  private int select(List<Individual> pop, List<Individual> popNext, int k) {
    popNext.addAll(params.getSelectOperator().select(pop, k));
    return k;
  }

  private void evalAndSort(List<Individual> pop) {
    pop.forEach(evaluator::eval);
    Collections.sort(pop);
  }

  private List<Individual> generateInitialPopulation() {
    List<Individual> population = new ArrayList<>(params.getPopulationSize());
    for (int i = 0; i < params.getMaxNumberOfIter(); i++) {
      population.add(generator.randomAutoIdent(params.getPopulationSize()));
    }
    return population;
  }

  @Override
  public Type getType() {
    return Type.SIMPLE_GA;
  }
}
