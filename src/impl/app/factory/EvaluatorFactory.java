package factory;

import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import factory.provider.OrientationResolverProvider;
import factory.provider.PlacingProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.Evaluator;
import models.dto.CreateComputationDto;

@Singleton
public class EvaluatorFactory implements Factory<CreateComputationDto, Evaluator> {

  private final ObjectiveFactory objectiveFactory;
  private final PlacingProvider placingProvider;
  private final InstanceParameterFactory instanceParameterFactory;
  private final OrientationResolverProvider orientationResolverProvider;


  @Inject
  public EvaluatorFactory(
      ObjectiveFactory objectiveFactory,
      PlacingProvider placingProvider,
      InstanceParameterFactory instanceParameterFactory,
      OrientationResolverProvider orientationResolverProvider) {
    this.objectiveFactory = objectiveFactory;
    this.placingProvider = placingProvider;
    this.instanceParameterFactory = instanceParameterFactory;
    this.orientationResolverProvider = orientationResolverProvider;
  }

  @Override
  public Evaluator create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException, FunctionNotValidException {
    return Evaluator.builder()
        .placing(placingProvider.get())
        .objective(objectiveFactory.create(dto))
        .params(instanceParameterFactory.create(dto.getInstanceParameters()))
        .orientationResolver(orientationResolverProvider.get())
        .build();
  }
}
