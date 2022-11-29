package factory.operators;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import factory.Factory;
import factory.provider.RandomProvider;
import logic.genetic.operators.mate.OnePointFullCrossover;
import logic.genetic.operators.mate.MatingOperator;
import logic.genetic.operators.mate.RepeatFirstParentMatingOperator;
import logic.metric.Metric;
import models.dto.GAParametersDto;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MatingOperatorFactory implements Factory<String, MatingOperator> {

  private final RandomProvider randomProvider;

  @Inject
  public MatingOperatorFactory(RandomProvider randomProvider) {
    this.randomProvider = randomProvider;
  }

  public MatingOperator create(GAParametersDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return create(dto.getMate());
  }

  @Override
  public MatingOperator create(String name)
      throws EntityNotFoundException, ImplementationNotFoundException {

    switch (findOrThrow(name)) {
      case REPEAT_FIRST_PARENT:
        return new RepeatFirstParentMatingOperator();
      case ONE_POINT_FULL_CROSSOVER:
        return new OnePointFullCrossover(randomProvider.get());
      default:
        throw new ImplementationNotFoundException(Metric.class, name);
    }
  }

  private MatingOperator.Type findOrThrow(String name) throws EntityNotFoundException {
    return MatingOperator.Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(Metric.class, name));
  }
}
