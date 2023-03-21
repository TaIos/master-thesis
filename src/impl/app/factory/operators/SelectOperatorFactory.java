package factory.operators;

import static logic.genetic.operators.select.SelectOperator.Type;

import exceptions.DtoConstraintViolationException;
import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import exceptions.InvalidFieldValueInJsonException;
import factory.Factory;
import factory.provider.GeneratorProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.GreedyGenerator;
import logic.genetic.operators.select.SelectBestOperator;
import logic.genetic.operators.select.SelectOperator;
import logic.genetic.operators.select.TournamentSelectionOperator;
import models.dto.GAParametersDto;

@Singleton
public class SelectOperatorFactory implements Factory<GAParametersDto, SelectOperator> {

  private final GeneratorProvider generatorProvider;

  @Inject
  public SelectOperatorFactory(GeneratorProvider generatorProvider) {
    this.generatorProvider = generatorProvider;
  }


  @Override
  public SelectOperator create(GAParametersDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException, DtoConstraintViolationException, FunctionNotValidException, InvalidFieldValueInJsonException {
    String name = dto.getSelect();

    switch (findOrThrow(name)) {
      case BEST:
        return new SelectBestOperator();
      case TOURNAMENT:
        return new TournamentSelectionOperator(new GreedyGenerator(generatorProvider.get()));
      default:
        throw new ImplementationNotFoundException(SelectOperator.class, name);
    }
  }

  private Type findOrThrow(String name) throws EntityNotFoundException {
    return Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(SelectOperator.class, name, Type.values()));
  }
}
