package factory;

import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import exceptions.InvalidFieldValueInJsonException;
import factory.provider.OrientationResolverProvider;
import factory.provider.PaintingSpaceAllocatorProvider;
import factory.provider.PointCopyFactoryProvider;
import factory.provider.RectangleCopyFactoryProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.Evaluator;
import models.dto.CreateComputationDto;

@Singleton
public class EvaluatorFactory implements Factory<CreateComputationDto, Evaluator> {

  private final ObjectiveFactory objectiveFactory;
  private final InstanceParameterFactory instanceParameterFactory;

  private final PaintingSpaceAllocatorProvider paintingSpaceAllocatorProvider;
  private final OrientationResolverProvider orientationResolverProvider;
  private final RectangleCopyFactoryProvider rectangleCopyFactoryProvider;
  private final PointCopyFactoryProvider pointCopyFactoryProvider;

  @Inject
  public EvaluatorFactory(ObjectiveFactory objectiveFactory,
      InstanceParameterFactory instanceParameterFactory,
      PaintingSpaceAllocatorProvider paintingSpaceAllocatorProvider,
      OrientationResolverProvider orientationResolverProvider,
      RectangleCopyFactoryProvider rectangleCopyFactoryProvider,
      PointCopyFactoryProvider pointCopyFactoryProvider) {
    this.objectiveFactory = objectiveFactory;
    this.instanceParameterFactory = instanceParameterFactory;
    this.paintingSpaceAllocatorProvider = paintingSpaceAllocatorProvider;
    this.orientationResolverProvider = orientationResolverProvider;
    this.rectangleCopyFactoryProvider = rectangleCopyFactoryProvider;
    this.pointCopyFactoryProvider = pointCopyFactoryProvider;
  }

  @Override
  public Evaluator create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException, FunctionNotValidException, InvalidFieldValueInJsonException {
    return Evaluator.builder()
        .placing(paintingSpaceAllocatorProvider.get())
        .objective(objectiveFactory.create(dto))
        .params(instanceParameterFactory.create(dto.getInstanceParameters()))
        .orientationResolver(orientationResolverProvider.get())
        .rectangleCopyFactory(rectangleCopyFactoryProvider.get())
        .pointCopyFactory(pointCopyFactoryProvider.get())
        .build();
  }
}
