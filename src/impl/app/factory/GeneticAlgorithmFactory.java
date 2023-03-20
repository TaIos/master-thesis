package factory;

import static logic.genetic.algorithm.GeneticAlgorithm.Type;

import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import exceptions.InvalidFieldValueInJsonException;
import factory.provider.GeneratorProvider;
import factory.provider.RandomProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.algorithm.GeneticAlgorithm;
import logic.genetic.algorithm.ProbabilisticBruteForce;
import logic.genetic.algorithm.SimpleGA;
import models.dto.CreateComputationDto;
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

  @Inject
  public GeneticAlgorithmFactory(GAParametersFactory gaParametersFactory,
      InstanceParameterFactory instanceParameterFactory, EvaluatorFactory evaluatorFactory,
      HallOfFameFactory hallOfFameFactory, CustomLoggerFactory loggerFactory,
      RandomStringGenerator randomStringGenerator,
      GeneratorProvider generatorProvider,
      RandomProvider randomProvider) {
    this.gaParametersFactory = gaParametersFactory;
    this.instanceParameterFactory = instanceParameterFactory;
    this.evaluatorFactory = evaluatorFactory;
    this.hallOfFameFactory = hallOfFameFactory;
    this.loggerFactory = loggerFactory;
    this.randomStringGenerator = randomStringGenerator;
    this.generatorProvider = generatorProvider;
    this.randomProvider = randomProvider;
  }


  @Override
  public GeneticAlgorithm create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException, FunctionNotValidException, InvalidFieldValueInJsonException {
    return create(
        dto, loggerFactory.create("implicit_ga_logger_" + randomStringGenerator.generate()));
  }

  public GeneticAlgorithm create(CreateComputationDto dto, Logger logger)
      throws EntityNotFoundException, ImplementationNotFoundException, FunctionNotValidException, InvalidFieldValueInJsonException {
    String name = dto.getGaParameters().getGeneticAlgorithm();
    switch (findOrThrow(name)) {
      case SIMPLE_GA:
        return new SimpleGA(
            gaParametersFactory.create(dto),
            instanceParameterFactory.create(dto.getInstanceParameters()),
            evaluatorFactory.create(dto), hallOfFameFactory.create(dto),
            generatorProvider.get(),
            randomProvider.get(),
            logger);
      case PROBABILISTIC_BRUTE:
        return new ProbabilisticBruteForce(
            gaParametersFactory.create(dto),
            instanceParameterFactory.create(dto.getInstanceParameters()),
            evaluatorFactory.create(dto), hallOfFameFactory.create(dto),
            generatorProvider.get(),
            randomProvider.get(),
            logger);
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
