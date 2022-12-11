package factory.operators;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import factory.Factory;
import factory.provider.RandomProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.operators.mutate.FlipOneSlicingOrderAtRandom;
import logic.genetic.operators.mutate.MutateOperator;
import logic.metric.Metric;
import models.dto.GAParametersDto;

@Singleton
public class MutateOperatorFactory implements Factory<String, MutateOperator> {

  private final RandomProvider randomProvider;

  @Inject
  public MutateOperatorFactory(RandomProvider randomProvider) {
    this.randomProvider = randomProvider;
  }

  public MutateOperator create(GAParametersDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return create(dto.getMutate());
  }

  @Override
  public MutateOperator create(String name)
      throws EntityNotFoundException, ImplementationNotFoundException {

    switch (findOrThrow(name)) {
      case FLIP_ONES_SLICING_ORDER_AT_RANDOM:
        return new FlipOneSlicingOrderAtRandom(randomProvider.get());
      default:
        throw new ImplementationNotFoundException(Metric.class, name);
    }
  }

  private MutateOperator.Type findOrThrow(String name) throws EntityNotFoundException {
    return MutateOperator.Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(Metric.class, name));
  }
}
