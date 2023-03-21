package logic.genetic.algorithm;

import factory.copy_factory.IndividualCopyFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import logic.genetic.Generator;
import logic.genetic.HallOfFame;
import logic.genetic.evaluator.Evaluator;
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
      Logger logger,
      IndividualCopyFactory individualCopyFactory) {
    super(gaParams, instanceParams, evaluator, hof, generator, rnd, logger);
    this.individualCopyFactory = individualCopyFactory;
  }

  private final IndividualCopyFactory individualCopyFactory;

  @Override
  public GAResult call() {
    Population pop = generateInitialPopulation();
    hof.log(pop, 0).withPrintLast(logger, gaParams);

    for (int iter = 1; iter <= gaParams.getMaxNumberOfIter(); iter++) {

      List<Individual> popNext = Stream.of(
              pop.getElite(), // TODO might create copy
              crossover(pop),
              mutate(pop),
              tournament(pop),
              random()
          ).parallel()
          .flatMap(Collection::stream)
          .collect(Collectors.toList());

      pop = new Population(popNext, evaluator, gaParams);
      hof.log(pop, iter).withPrintLast(logger, gaParams);
    }

    return new GAResult(hof);
  }

  // TODO parallel
  private List<Individual> crossover(Population pop) {
    List<Individual> children = new ArrayList<>(gaParams.getCounts().getChildren());
    for (int i = 0; i < gaParams.getCounts().getChildren(); i++) {
      Individual p1 = pop.getElite().get(rnd.nextInt(pop.getElite().size()));
      Individual p2 = pop.getAverage().get(rnd.nextInt(pop.getElite().size()));
      children.add(gaParams.getMatingOperator().mate(p1, p2));
    }
    return children;
  }

  // TODO parallel
  private List<Individual> mutate(Population pop) {
    List<Individual> mutants = new ArrayList<>(gaParams.getCounts().getMutant());
    for (int i = 0; i < gaParams.getCounts().getMutant(); i++) {
      int idx = rnd.nextInt(pop.getElite().size() + pop.getAverage().size());
      final Individual toMutate;
      if (idx < pop.getElite().size()) {
        toMutate = pop.getElite().get(idx);
      } else {
        toMutate = pop.getAverage().get(idx);
      }
      Individual toMutateCopy = individualCopyFactory.createCopy(toMutate);
      gaParams.getMutateOperator().mutate(toMutateCopy);
      mutants.add(toMutateCopy);
    }
    return mutants;
  }

  // TODO impl
  // TODO parallel
  private List<Individual> tournament(Population pop) {
    List<Individual> winners = new ArrayList<>(gaParams.getCounts().getWinner());
    for (int i = 0; i < gaParams.getCounts().getWinner(); i++) {
      winners.add(pop.getWorst().get(rnd.nextInt(pop.getWorst().size())));
    }
    return winners;
  }

  private List<Individual> random() {
    return generator.generateRandomIndividualList(instanceParams.getPaintings(),
        gaParams.getCounts().getRandom());
  }

  private Population generateInitialPopulation() {
    return new Population(generator.generateRandomIndividualList(instanceParams.getPaintings(),
        gaParams.getPopulationSize()), evaluator, gaParams);
  }


  @Override
  public Type getType() {
    return Type.SIMPLE_GA;
  }
}
