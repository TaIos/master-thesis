package factory;

import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import exceptions.InvalidFieldValueInJsonException;
import factory.provider.CalculateOverlappingPaintingsPartProvider;
import factory.provider.EuclideanMetricProvider;
import factory.provider.IsOutsideOfAllocatedAreaObjectivePartProvider;
import factory.provider.PaintingFlowSumProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.objective.Objective;
import logic.objective.Objective.ObjectiveType;
import logic.objective.SimpleObjective;
import models.dto.CreateComputationDto;

@Singleton
public class ObjectiveFactory implements Factory<CreateComputationDto, Objective> {

  private final FunctionThreadSafeWrapperFactory functionThreadSafeWrapperFactory;
  private final SimpleObjectiveParametersFactory simpleObjectiveParametersFactory;

  private final IsOutsideOfAllocatedAreaObjectivePartProvider isOutsideOfAllocatedAreaObjectivePartProvider;
  private final CalculateOverlappingPaintingsPartProvider calculateOverlappingPaintingsPartProvider;
  private final PaintingFlowSumProvider paintingFlowSumProvider;
  private final EuclideanMetricProvider euclideanMetricProvider;

  @Inject
  public ObjectiveFactory(
      FunctionThreadSafeWrapperFactory functionThreadSafeWrapperFactory,
      SimpleObjectiveParametersFactory simpleObjectiveParametersFactory,
      IsOutsideOfAllocatedAreaObjectivePartProvider isOutsideOfAllocatedAreaObjectivePartProvider,
      CalculateOverlappingPaintingsPartProvider calculateOverlappingPaintingsPartProvider,
      PaintingFlowSumProvider paintingFlowSumProvider,
      EuclideanMetricProvider euclideanMetricProvider) {
    this.functionThreadSafeWrapperFactory = functionThreadSafeWrapperFactory;
    this.simpleObjectiveParametersFactory = simpleObjectiveParametersFactory;
    this.isOutsideOfAllocatedAreaObjectivePartProvider = isOutsideOfAllocatedAreaObjectivePartProvider;
    this.calculateOverlappingPaintingsPartProvider = calculateOverlappingPaintingsPartProvider;
    this.paintingFlowSumProvider = paintingFlowSumProvider;
    this.euclideanMetricProvider = euclideanMetricProvider;
  }

  @Override
  public Objective create(CreateComputationDto dto)
      throws ImplementationNotFoundException, EntityNotFoundException, FunctionNotValidException, InvalidFieldValueInJsonException {
    String name = dto.getObjectiveParameters().getName();
    switch (findOrThrow(name)) {
      case SIMPLE:
        return createSimpleObjective(dto);
      default:
        throw new ImplementationNotFoundException(Objective.class, name);
    }
  }

  private SimpleObjective createSimpleObjective(CreateComputationDto dto)
      throws FunctionNotValidException, InvalidFieldValueInJsonException {
    return new SimpleObjective(
        simpleObjectiveParametersFactory.create(dto.getObjectiveParameters()),
        functionThreadSafeWrapperFactory.create(dto),
        euclideanMetricProvider.get(),
        isOutsideOfAllocatedAreaObjectivePartProvider.get(),
        calculateOverlappingPaintingsPartProvider.get(),
        paintingFlowSumProvider.get()
    );

  }

  private ObjectiveType findOrThrow(String name) throws EntityNotFoundException {
    return ObjectiveType.getForLabel(name)
        .orElseThrow(
            () -> new EntityNotFoundException(Objective.class, name, ObjectiveType.values()));
  }
}
