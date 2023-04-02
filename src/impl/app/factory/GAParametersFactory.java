package factory;

import exceptions.DtoConstraintViolationException;
import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import exceptions.InvalidFieldValueInJsonException;
import factory.operators.MatingOperatorFactory;
import factory.operators.MutateOperatorFactory;
import factory.operators.SelectOperatorFactory;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.dto.GAParametersDto;
import models.entity.GAParameters;

@Singleton
public class GAParametersFactory implements Factory<GAParametersDto, GAParameters> {

  private final MatingOperatorFactory matingOperatorFactory;
  private final MutateOperatorFactory mutateOperatorFactory;
  private final SelectOperatorFactory selectOperatorFactory;
  private final PopulationDivisionCountsFactory populationDivisionCountsFactory;
  private final InitialPopulationDivisionCountsFactory initialPopulationDivisionCountsFactory;

  @Inject
  public GAParametersFactory(
      MatingOperatorFactory matingOperatorFactory,
      MutateOperatorFactory mutateOperatorFactory,
      SelectOperatorFactory selectOperatorFactory,
      PopulationDivisionCountsFactory populationDivisionCountsFactory,
      InitialPopulationDivisionCountsFactory initialPopulationDivisionCountsFactory) {
    this.matingOperatorFactory = matingOperatorFactory;
    this.mutateOperatorFactory = mutateOperatorFactory;
    this.selectOperatorFactory = selectOperatorFactory;
    this.populationDivisionCountsFactory = populationDivisionCountsFactory;
    this.initialPopulationDivisionCountsFactory = initialPopulationDivisionCountsFactory;
  }

  @Override
  public GAParameters create(GAParametersDto dto)
      throws DtoConstraintViolationException, EntityNotFoundException, ImplementationNotFoundException, FunctionNotValidException, InvalidFieldValueInJsonException {
    return GAParameters.builder()
        .maxNumberOfIter(dto.getMaxNumberOfIter())
        .populationSize(dto.getPopulationSize())
        .maximumWildCardCount(dto.getMaximumWildCardCount())
        .counts(populationDivisionCountsFactory.create(dto))
        .initialCounts(initialPopulationDivisionCountsFactory.create(dto))
        .matingOperator(matingOperatorFactory.create(dto))
        .mutateOperator(mutateOperatorFactory.create(dto))
        .selectOperator(selectOperatorFactory.create(dto))
        .build();
  }

}
