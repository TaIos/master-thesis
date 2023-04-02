package logic.genetic.algorithm;

import static utils.JavaUtils.shuffle;

import factory.copy_factory.IndividualCopyFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import logic.genetic.HallOfFame;
import logic.genetic.evaluator.Evaluator;
import logic.genetic.generator.Generator;
import models.entity.EvaluatedIndividual;
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
    hof.log(pop, 0, gaParams).withPrintLast(logger, gaParams);

    for (int iter = 1; iter <= gaParams.getMaxNumberOfIter(); iter++) {
      List<Individual> popNext = Stream.of(
              pop.getElite(),
              crossover(pop),
              mutate(pop),
              select(pop),
              random())
          .flatMap(Collection::stream)
          .collect(Collectors.toList());

      pop = new Population(popNext, evaluator, gaParams.getCounts());
      hof.log(pop, iter, gaParams).withPrintLast(logger, gaParams);
    }
    return new GAResult(hof);
  }

  private List<Individual> crossover(Population pop) {
    List<Pair<EvaluatedIndividual, EvaluatedIndividual>> parents = new ArrayList<>(
        gaParams.getCounts().getChildren());
    for (int i = 0; i < gaParams.getCounts().getChildren(); i++) {
      EvaluatedIndividual p1 = pop.getEliteEvaluated().get(rnd.nextInt(pop.getElite().size()));
      EvaluatedIndividual p2 = pop.getAverageEvaluated().get(rnd.nextInt(pop.getElite().size()));
      parents.add(Pair.of(p1, p2));
    }
    return parents.parallelStream()
        .map(pair -> {
          EvaluatedIndividual p1 = pair.getLeft();
          EvaluatedIndividual p2 = pair.getRight();
          double o1 = p1.getLayout().getObjectiveValue();
          double o2 = p2.getLayout().getObjectiveValue();
          double o12 = o1 + o2;

          // TODO check or implement scaling using sigmoid
          return gaParams.getMatingOperator().mate(
              p1.getIndividual(),
              p2.getIndividual(),
              o2 / o12, // switched because objective value is better when it is lower
              o1 / o12);
        })
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

  private List<Individual> select(Population pop) {
    return gaParams.getSelectOperator()
        .select(pop.getWorst(), gaParams.getCounts().getWorst(), evaluator);
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
