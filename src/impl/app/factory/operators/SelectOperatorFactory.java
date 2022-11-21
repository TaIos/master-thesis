package factory.operators;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import factory.Factory;
import logic.genetic.operators.select.SelectBestOperator;
import logic.genetic.operators.select.SelectOperator;
import logic.metric.Metric;
import models.dto.GAParametersDto;

import javax.inject.Singleton;

@Singleton
public class SelectOperatorFactory implements Factory<String, SelectOperator> {

  @Override
  public SelectOperator create(String name)
      throws EntityNotFoundException, ImplementationNotFoundException {

    switch (findOrThrow(name)) {
      case BEST:
        return new SelectBestOperator();
      default:
        throw new ImplementationNotFoundException(Metric.class, name);
    }
  }

  public SelectOperator create(GAParametersDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return create(dto.getSelect());
  }

  private SelectOperator.Type findOrThrow(String name) throws EntityNotFoundException {
    return SelectOperator.Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(Metric.class, name));
  }
}
