package factory;

import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import factory.provider.CalculateOverlappingPaintingsPartProvider;
import factory.provider.IsOutsideOfAllocatedAreaObjectivePartProvider;
import factory.provider.PaintingLocalPlacerProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.objective.Objective;
import logic.objective.SimpleObjective;
import models.dto.CreateComputationDto;

@Singleton
public class ObjectiveFactory implements Factory<CreateComputationDto, Objective> {

  private final PaintingLocalPlacerProvider paintingLocalPlacerProvider;
  private final FunctionThreadSafeWrapperFactory functionThreadSafeWrapperFactory;

  // parts
  private final IsOutsideOfAllocatedAreaObjectivePartProvider isOutsideOfAllocatedAreaObjectivePartProvider;
  private final CalculateOverlappingPaintingsPartProvider calculateOverlappingPaintingsPartProvider;

  @Inject
  public ObjectiveFactory(
      PaintingLocalPlacerProvider paintingLocalPlacerProvider,
      FunctionThreadSafeWrapperFactory functionThreadSafeWrapperFactory,
      IsOutsideOfAllocatedAreaObjectivePartProvider isOutsideOfAllocatedAreaObjectivePartProvider,
      CalculateOverlappingPaintingsPartProvider calculateOverlappingPaintingsPartProvider) {
    this.paintingLocalPlacerProvider = paintingLocalPlacerProvider;
    this.functionThreadSafeWrapperFactory = functionThreadSafeWrapperFactory;
    this.isOutsideOfAllocatedAreaObjectivePartProvider = isOutsideOfAllocatedAreaObjectivePartProvider;
    this.calculateOverlappingPaintingsPartProvider = calculateOverlappingPaintingsPartProvider;
  }

  @Override
  public Objective create(CreateComputationDto dto)
      throws ImplementationNotFoundException, EntityNotFoundException, FunctionNotValidException {
    String name = dto.getGaParams().getObjective();
    switch (findOrThrow(name)) {
      case SIMPLE:
        return createSimpleObjective(dto);
      default:
        throw new ImplementationNotFoundException(Objective.class, name);
    }
  }

  private SimpleObjective createSimpleObjective(CreateComputationDto dto)
      throws FunctionNotValidException {
    return new SimpleObjective(
        functionThreadSafeWrapperFactory.create(dto),
        paintingLocalPlacerProvider.get(), isOutsideOfAllocatedAreaObjectivePartProvider.get(),
        calculateOverlappingPaintingsPartProvider.get());

  }

  private Objective.Type findOrThrow(String name) throws EntityNotFoundException {
    return Objective.Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(Objective.class, name));
  }
}
