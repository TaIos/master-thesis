package factory;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import factory.provider.GeneratorProvider;
import factory.provider.RandomProvider;
import logic.genetic.Evaluator;
import logic.genetic.HallOfFame;
import logic.genetic.algorithm.GeneticAlgorithm;
import logic.genetic.algorithm.SimpleGA;
import models.dto.CreateComputationDto;
import models.entity.GAParameters;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GeneticAlgorithmFactory implements Factory<CreateComputationDto, GeneticAlgorithm> {

  private final GAParametersFactory gaParametersFactory;
  private final EvaluatorFactory evaluatorFactory;
  private final GeneratorProvider generatorProvider;
  private final RandomProvider randomProvider;

  @Inject
  public GeneticAlgorithmFactory(
      GAParametersFactory gaParametersFactory,
      EvaluatorFactory evaluatorFactory,
      GeneratorProvider generatorProvider,
      RandomProvider randomProvider) {
    this.gaParametersFactory = gaParametersFactory;
    this.evaluatorFactory = evaluatorFactory;
    this.generatorProvider = generatorProvider;
    this.randomProvider = randomProvider;
  }

  @Override
  public GeneticAlgorithm create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return create(
        dto.getGaParams().getGeneticAlgorithm(),
        gaParametersFactory.create(dto),
        evaluatorFactory.create(dto),
        new HallOfFame());
  }

  public GeneticAlgorithm create(
      String name, GAParameters params, Evaluator evaluator, HallOfFame hof)
      throws EntityNotFoundException, ImplementationNotFoundException {
    switch (findOrThrow(name)) {
      case SIMPLE_GA:
        return new SimpleGA(params, evaluator, hof, generatorProvider.get(), randomProvider.get());
      default:
        throw new ImplementationNotFoundException(GeneticAlgorithm.class, name);
    }
  }

  private GeneticAlgorithm.Type findOrThrow(String name) throws EntityNotFoundException {
    return GeneticAlgorithm.Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(GeneticAlgorithm.class, name));
  }
}
