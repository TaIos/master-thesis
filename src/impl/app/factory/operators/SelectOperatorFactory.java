package factory.operators;

import static logic.genetic.operators.select.SelectOperator.Type;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import factory.Factory;
import javax.inject.Singleton;
import logic.genetic.operators.select.SelectBestOperator;
import logic.genetic.operators.select.SelectOperator;
import models.dto.GAParametersDto;

@Singleton
public class SelectOperatorFactory implements Factory<String, SelectOperator> {

  @Override
  public SelectOperator create(String name)
      throws EntityNotFoundException, ImplementationNotFoundException {

    if (findOrThrow(name) == Type.BEST) {
      return new SelectBestOperator();
    }
    throw new ImplementationNotFoundException(SelectOperator.class, name);
  }

  public SelectOperator create(GAParametersDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return create(dto.getSelect());
  }

  private Type findOrThrow(String name) throws EntityNotFoundException {
    return Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(SelectOperator.class, name, Type.values()));
  }
}
