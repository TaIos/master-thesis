package factory;

import static logic.genetic.evaluator.Evaluator.Type;

import exceptions.DtoConstraintViolationException;
import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import exceptions.InvalidFieldValueInJsonException;
import factory.provider.IndividualResolverProvider;
import factory.provider.ObjectiveValueComparatorProvider;
import factory.provider.PaintingSpaceAllocatorProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.evaluator.Evaluator;
import logic.genetic.evaluator.GaEvaluator;
import models.dto.CreateComputationDto;

@Singleton
public class EvaluatorFactory implements Factory<CreateComputationDto, Evaluator> {

  private final ObjectiveFactory objectiveFactory;
  private final InstanceParameterFactory instanceParameterFactory;

  private final PaintingSpaceAllocatorProvider paintingSpaceAllocatorProvider;
  private final IndividualResolverProvider individualResolverProvider;
  private final PlacingHeuristicsFactory placingHeuristicsFactory;
  private final ObjectiveValueComparatorProvider objectiveValueComparatorProvider;

  @Inject
  public EvaluatorFactory(ObjectiveFactory objectiveFactory,
      InstanceParameterFactory instanceParameterFactory,
      PaintingSpaceAllocatorProvider paintingSpaceAllocatorProvider,
      IndividualResolverProvider individualResolverProvider,
      PlacingHeuristicsFactory placingHeuristicsFactory,
      ObjectiveValueComparatorProvider objectiveValueComparatorProvider) {
    this.objectiveFactory = objectiveFactory;
    this.instanceParameterFactory = instanceParameterFactory;
    this.paintingSpaceAllocatorProvider = paintingSpaceAllocatorProvider;
    this.individualResolverProvider = individualResolverProvider;
    this.placingHeuristicsFactory = placingHeuristicsFactory;
    this.objectiveValueComparatorProvider = objectiveValueComparatorProvider;
  }

  @Override
  public Evaluator create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException, FunctionNotValidException, InvalidFieldValueInJsonException, DtoConstraintViolationException {
    String name = dto.getGaParameters().getEvaluator();
    switch (findOrThrow(name)) {
      case GENETIC:
        return new GaEvaluator(individualResolverProvider.get(),
            paintingSpaceAllocatorProvider.get(),
            placingHeuristicsFactory.create(dto),
            objectiveFactory.create(dto),
            objectiveValueComparatorProvider.get(),
            instanceParameterFactory.create(dto.getInstanceParameters()),
            dto.getGaParameters().getMaximumWildCardCount());
      default:
        throw new ImplementationNotFoundException(Evaluator.class, name);
    }
  }

  private Type findOrThrow(String name) throws EntityNotFoundException {
    return Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(Evaluator.class, name, Type.values()));
  }
}
