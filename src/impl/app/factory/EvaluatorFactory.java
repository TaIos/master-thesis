package factory;

import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import exceptions.InvalidFieldValueInJsonException;
import factory.provider.IndividualResolverProvider;
import factory.provider.ObjectiveValueComparatorProvider;
import factory.provider.PaintingSpaceAllocatorProvider;
import factory.provider.PlacingHeuristicsProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.Evaluator;
import models.dto.CreateComputationDto;

@Singleton
public class EvaluatorFactory implements Factory<CreateComputationDto, Evaluator> {

  private final ObjectiveFactory objectiveFactory;
  private final InstanceParameterFactory instanceParameterFactory;

  private final PaintingSpaceAllocatorProvider paintingSpaceAllocatorProvider;
  private final IndividualResolverProvider individualResolverProvider;
  private final PlacingHeuristicsProvider placingHeuristicsProvider;
  private final ObjectiveValueComparatorProvider objectiveValueComparatorProvider;

  @Inject
  public EvaluatorFactory(ObjectiveFactory objectiveFactory,
      InstanceParameterFactory instanceParameterFactory,
      PaintingSpaceAllocatorProvider paintingSpaceAllocatorProvider,
      IndividualResolverProvider individualResolverProvider,
      PlacingHeuristicsProvider placingHeuristicsProvider,
      ObjectiveValueComparatorProvider objectiveValueComparatorProvider) {
    this.objectiveFactory = objectiveFactory;
    this.instanceParameterFactory = instanceParameterFactory;
    this.paintingSpaceAllocatorProvider = paintingSpaceAllocatorProvider;
    this.individualResolverProvider = individualResolverProvider;
    this.placingHeuristicsProvider = placingHeuristicsProvider;
    this.objectiveValueComparatorProvider = objectiveValueComparatorProvider;
  }

  @Override
  public Evaluator create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException, FunctionNotValidException, InvalidFieldValueInJsonException {
    return Evaluator.builder()
        .individualResolver(individualResolverProvider.get())
        .paintingSpaceAllocator(paintingSpaceAllocatorProvider.get())
        .placingHeuristics(placingHeuristicsProvider.get())
        .objective(objectiveFactory.create(dto))
        .objectiveValueComparator(objectiveValueComparatorProvider.get())
        .params(instanceParameterFactory.create(dto.getInstanceParameters()))
        .build();
  }
}
