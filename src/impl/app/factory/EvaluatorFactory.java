package factory;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import factory.provider.PlacingProvider;
import logic.genetic.Evaluator;
import models.dto.CreateComputationDto;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EvaluatorFactory implements Factory<CreateComputationDto, Evaluator> {
  private final ObjectiveFactory objectiveFactory;
  private final PlacingProvider placingProvider;
  private final InstanceParameterFactory instanceParameterFactory;

  @Inject
  public EvaluatorFactory(
      ObjectiveFactory objectiveFactory,
      PlacingProvider placingProvider,
      InstanceParameterFactory instanceParameterFactory) {
    this.objectiveFactory = objectiveFactory;
    this.placingProvider = placingProvider;
    this.instanceParameterFactory = instanceParameterFactory;
  }

  @Override
  public Evaluator create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return Evaluator.builder()
        .placing(placingProvider.get())
        .objective(objectiveFactory.create(dto))
        .params(instanceParameterFactory.create(dto))
        .build();
  }
}
