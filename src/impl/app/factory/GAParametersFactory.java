package factory;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import factory.operators.MatingOperatorFactory;
import factory.operators.MutateOperatorFactory;
import factory.operators.SelectOperatorFactory;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.dto.CreateComputationDto;
import models.dto.GAParametersDto;
import models.entity.GAParameters;

@Singleton
public class GAParametersFactory implements Factory<GAParametersDto, GAParameters> {

  private final MatingOperatorFactory matingOperatorFactory;
  private final MutateOperatorFactory mutateOperatorFactory;
  private final SelectOperatorFactory selectOperatorFactory;

  @Inject
  public GAParametersFactory(
      MatingOperatorFactory matingOperatorFactory,
      MutateOperatorFactory mutateOperatorFactory,
      SelectOperatorFactory selectOperatorFactory) {
    this.matingOperatorFactory = matingOperatorFactory;
    this.mutateOperatorFactory = mutateOperatorFactory;
    this.selectOperatorFactory = selectOperatorFactory;
  }

  @Override
  public GAParameters create(GAParametersDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return GAParameters.builder()
        .mutationProb(dto.getMutationProb())
        .crossoverProb(dto.getCrossoverProb())
        .maxNumberOfIter(dto.getMaxNumberOfIter())
        .matingOperator(matingOperatorFactory.create(dto))
        .mutateOperator(mutateOperatorFactory.create(dto))
        .selectOperator(selectOperatorFactory.create(dto))
        .populationSize(dto.getPopulationSize())
        .build();
  }

  public GAParameters create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return create(dto.getGaParameters());
  }
}
