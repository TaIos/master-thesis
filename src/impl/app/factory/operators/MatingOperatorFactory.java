package factory.operators;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import factory.Factory;
import factory.NormalizedProbabilityVectorSumCrossoverParametersFactory;
import factory.provider.IndividualCopyFactoryProvider;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.operators.mate.MatingOperator;
import logic.genetic.operators.mate.MatingOperator.Type;
import logic.genetic.operators.mate.NormalizedProbabilityVectorSumCrossover;
import logic.genetic.operators.mate.RepeatFirstParentMatingOperator;
import models.dto.GAParametersDto;

@Singleton
public class MatingOperatorFactory implements Factory<GAParametersDto, MatingOperator> {

  private final NormalizedProbabilityVectorSumCrossoverParametersFactory normalizedProbabilityVectorSumCrossoverParametersFactory;
  private final IndividualCopyFactoryProvider individualCopyFactoryProvider;

  @Inject
  public MatingOperatorFactory(
      NormalizedProbabilityVectorSumCrossoverParametersFactory normalizedProbabilityVectorSumCrossoverParametersFactory,
      IndividualCopyFactoryProvider individualCopyFactoryProvider) {
    this.normalizedProbabilityVectorSumCrossoverParametersFactory = normalizedProbabilityVectorSumCrossoverParametersFactory;
    this.individualCopyFactoryProvider = individualCopyFactoryProvider;
  }

  @Override
  public MatingOperator create(GAParametersDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    switch (findOrThrow(dto.getMate())) {
      case REPEAT_FIRST_PARENT:
        return new RepeatFirstParentMatingOperator(individualCopyFactoryProvider.get());
      case NORMALIZED_PROBABILITY_VECTOR_SUM_CROSSOVER:
        return new NormalizedProbabilityVectorSumCrossover(
            normalizedProbabilityVectorSumCrossoverParametersFactory.create(dto));
      default:
        throw new ImplementationNotFoundException(MatingOperator.class, dto.getMate());
    }
  }

  private MatingOperator.Type findOrThrow(String name) throws EntityNotFoundException {
    String availableValues = Arrays.stream(MatingOperator.Type.values()).map(Type::getLabel)
        .collect(
            Collectors.joining(","));
    return MatingOperator.Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(MatingOperator.class, name,
            "Available values: " + availableValues));
  }
}
