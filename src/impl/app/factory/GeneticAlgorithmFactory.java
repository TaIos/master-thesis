package factory;

import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import exceptions.InvalidFieldValueInJsonException;
import factory.provider.BestIndividualFactoryProvider;
import factory.provider.GeneratorProvider;
import factory.provider.RandomProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.Evaluator;
import logic.genetic.HallOfFame;
import logic.genetic.algorithm.GeneticAlgorithm;
import logic.genetic.algorithm.SimpleGA;
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
  private final RandomStringGenerator randomStringGenerator;

  private final GeneratorProvider generatorProvider;
  private final RandomProvider randomProvider;
  private final BestIndividualFactoryProvider bestIndividualFactoryProvider;

  @Inject
  public GeneticAlgorithmFactory(GAParametersFactory gaParametersFactory,
      InstanceParameterFactory instanceParameterFactory, EvaluatorFactory evaluatorFactory,
      HallOfFameFactory hallOfFameFactory, CustomLoggerFactory loggerFactory,
      RandomStringGenerator randomStringGenerator, GeneratorProvider generatorProvider,
      RandomProvider randomProvider, BestIndividualFactoryProvider bestIndividualFactoryProvider) {
    this.gaParametersFactory = gaParametersFactory;
    this.instanceParameterFactory = instanceParameterFactory;
    this.evaluatorFactory = evaluatorFactory;
    this.hallOfFameFactory = hallOfFameFactory;
    this.loggerFactory = loggerFactory;
    this.randomStringGenerator = randomStringGenerator;
    this.generatorProvider = generatorProvider;
    this.randomProvider = randomProvider;
    this.bestIndividualFactoryProvider = bestIndividualFactoryProvider;
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
        logger);
  }

  public GeneticAlgorithm create(
      String name,
      GAParameters gaParams,
      InstanceParameters instanceParams,
      Evaluator evaluator,
      HallOfFame hof,
      Logger logger)
      throws EntityNotFoundException, ImplementationNotFoundException {
    if (findOrThrow(name) == GeneticAlgorithm.Type.SIMPLE_GA) {
      return new SimpleGA(
          gaParams,
          instanceParams,
          evaluator,
          hof,
          generatorProvider.get(),
          randomProvider.get(),
          logger,
          bestIndividualFactoryProvider.get());
    }
    throw new ImplementationNotFoundException(GeneticAlgorithm.class, name);
  }

  private GeneticAlgorithm.Type findOrThrow(String name) throws EntityNotFoundException {
    return GeneticAlgorithm.Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(GeneticAlgorithm.class, name));
  }
}
