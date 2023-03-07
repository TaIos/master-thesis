package factory;

import static logic.genetic.algorithm.GeneticAlgorithm.Type;

import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import exceptions.InvalidFieldValueInJsonException;
import factory.provider.GeneratorProvider;
import factory.provider.ObjectiveValueComparatorProvider;
import factory.provider.RandomProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.HallOfFame;
import logic.genetic.algorithm.BruteForce;
import logic.genetic.algorithm.GeneticAlgorithm;
import logic.genetic.algorithm.SimpleGA;
import logic.genetic.evaluator.Evaluator;
import logic.objective.Objective;
import logic.objective.ObjectiveValueComparator;
import models.dto.CreateComputationDto;
import models.entity.GAParameters;
import models.entity.InstanceParameters;
import org.slf4j.Logger;
import utils.RandomStringGenerator;

@Singleton
public class GeneticAlgorithmFactory implements Factory<CreateComputationDto, GeneticAlgorithm> {

  private final GAParametersFactory gaParametersFactory;
  private final InstanceParameterFactory instanceParameterFactory;
  private final EvaluatorFactory evaluatorFactory;
  private final HallOfFameFactory hallOfFameFactory;
  private final CustomLoggerFactory loggerFactory;
  private final ObjectiveFactory objectiveFactory;
  private final RandomStringGenerator randomStringGenerator;

  private final GeneratorProvider generatorProvider;
  private final RandomProvider randomProvider;
  private final ObjectiveValueComparatorProvider objectiveValueComparatorProvider;

  @Inject
  public GeneticAlgorithmFactory(GAParametersFactory gaParametersFactory,
      InstanceParameterFactory instanceParameterFactory, EvaluatorFactory evaluatorFactory,
      HallOfFameFactory hallOfFameFactory, CustomLoggerFactory loggerFactory,
      ObjectiveFactory objectiveFactory, RandomStringGenerator randomStringGenerator,
      GeneratorProvider generatorProvider,
      RandomProvider randomProvider,
      ObjectiveValueComparatorProvider objectiveValueComparatorProvider) {
    this.gaParametersFactory = gaParametersFactory;
    this.instanceParameterFactory = instanceParameterFactory;
    this.evaluatorFactory = evaluatorFactory;
    this.hallOfFameFactory = hallOfFameFactory;
    this.loggerFactory = loggerFactory;
    this.objectiveFactory = objectiveFactory;
    this.randomStringGenerator = randomStringGenerator;
    this.generatorProvider = generatorProvider;
    this.randomProvider = randomProvider;
    this.objectiveValueComparatorProvider = objectiveValueComparatorProvider;
  }


  @Override
  public GeneticAlgorithm create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException, FunctionNotValidException, InvalidFieldValueInJsonException {
    return create(
        dto, loggerFactory.create("implicit_ga_logger_" + randomStringGenerator.generate()));
  }

  public GeneticAlgorithm create(CreateComputationDto dto, Logger logger)
      throws EntityNotFoundException, ImplementationNotFoundException, FunctionNotValidException, InvalidFieldValueInJsonException {
    return create(
        dto.getGaParameters().getGeneticAlgorithm(),
        gaParametersFactory.create(dto),
        instanceParameterFactory.create(dto.getInstanceParameters()),
        evaluatorFactory.create(dto),
        hallOfFameFactory.create(dto),
        objectiveFactory.create(dto),
        logger);
  }

  private GeneticAlgorithm create(String name, GAParameters gaParams,
      InstanceParameters instanceParams, Evaluator evaluator, HallOfFame hof, Objective objective,
      Logger logger)
      throws EntityNotFoundException, ImplementationNotFoundException {
    switch (findOrThrow(name)) {
      case SIMPLE_GA:
        return new SimpleGA(gaParams, instanceParams, evaluator, hof, generatorProvider.get(),
            randomProvider.get(), logger);
      case BRUTE:
        return new BruteForce(gaParams, instanceParams, evaluator, hof, generatorProvider.get(),
            randomProvider.get(), logger, objective, objectiveValueComparatorProvider.get());
      default:
        throw new ImplementationNotFoundException(GeneticAlgorithm.class, name);
    }
  }

  private Type findOrThrow(String name) throws EntityNotFoundException {
    return Type.getForLabel(name)
        .orElseThrow(
            () -> new EntityNotFoundException(GeneticAlgorithm.class, name, Type.values()));
  }
}
