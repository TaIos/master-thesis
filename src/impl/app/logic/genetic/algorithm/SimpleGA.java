package logic.genetic.algorithm;

import static utils.JavaUtils.shuffle;

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
import org.apache.commons.lang3.tuple.Pair;
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
              pop.getElite(),
              crossover(pop),
              mutate(pop),
              tournament(pop),
              random())
          .flatMap(Collection::stream)
          .collect(Collectors.toList());

      pop = new Population(popNext, evaluator, gaParams.getCounts());
      hof.log(pop, iter).withPrintLast(logger, gaParams);
    }
    return new GAResult(hof);
  }

  private List<Individual> crossover(Population pop) {
    List<Pair<Individual, Individual>> parents = new ArrayList<>(
        gaParams.getCounts().getChildren());
    for (int i = 0; i < gaParams.getCounts().getChildren(); i++) {
      Individual p1 = pop.getElite().get(rnd.nextInt(pop.getElite().size()));
      Individual p2 = pop.getAverage().get(rnd.nextInt(pop.getElite().size()));
      parents.add(Pair.of(p1, p2));
    }
    return parents.parallelStream()
        .map(pair -> gaParams.getMatingOperator().mate(pair.getLeft(), pair.getRight()))
        .collect(Collectors.toList());
  }

  private List<Individual> mutate(Population pop) {
    return shuffle(Stream.concat(pop.getElite().stream(), pop.getAverage().stream())
        .collect(Collectors.toList()), rnd)
        .subList(0, gaParams.getCounts().getMutant())
        .parallelStream()
        .map(individualCopyFactory::createCopy)
        .peek(indCopy -> gaParams.getMutateOperator().mutate(indCopy)).collect(Collectors.toList());

  }

  private List<Individual> tournament(Population pop) {
    return gaParams.getSelectOperator().select(pop.getWorst(), pop.getWorst().size(), evaluator);
  }

  private List<Individual> random() {
    return generator.generateRandomIndividualList(instanceParams.getPaintings(),
        gaParams.getCounts().getRandom());
  }

  private Population generateInitialPopulation() {
    return new Population(generator.generateRandomIndividualList(instanceParams.getPaintings(),
        gaParams.getPopulationSize()), evaluator, gaParams.getCounts());
  }


  @Override
  public Type getType() {
    return Type.SIMPLE_GA;
  }
}
