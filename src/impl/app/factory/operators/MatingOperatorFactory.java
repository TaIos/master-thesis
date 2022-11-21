package factory.operators;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import factory.Factory;
import logic.genetic.operators.mate.MatingOperator;
import logic.genetic.operators.mate.RepeatFirstParentMatingOperator;
import logic.metric.Metric;
import models.dto.GAParametersDto;

import javax.inject.Singleton;

@Singleton
public class MatingOperatorFactory implements Factory<String, MatingOperator> {

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
      default:
        throw new ImplementationNotFoundException(Metric.class, name);
    }
  }

  private MatingOperator.Type findOrThrow(String name) throws EntityNotFoundException {
    return MatingOperator.Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(Metric.class, name));
  }
}
