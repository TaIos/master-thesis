package factory;

import static logic.genetic.evaluator.Evaluator.Type;

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
import logic.genetic.evaluator.Evaluator;
import logic.genetic.evaluator.GaEvaluator;
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
    String name = dto.getGaParameters().getEvaluator();
    switch (findOrThrow(name)) {
      case GENETIC:
        return new GaEvaluator(individualResolverProvider.get(),
            paintingSpaceAllocatorProvider.get(),
            placingHeuristicsProvider.get(),
            objectiveFactory.create(dto),
            objectiveValueComparatorProvider.get(),
            instanceParameterFactory.create(dto.getInstanceParameters()));
      default:
        throw new ImplementationNotFoundException(Evaluator.class, name);
    }
  }

  private Type findOrThrow(String name) throws EntityNotFoundException {
    return Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(Evaluator.class, name, Type.values()));
  }
}
