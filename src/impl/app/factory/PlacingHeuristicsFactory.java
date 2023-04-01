package factory;

import static logic.genetic.placing.PlacingHeuristics.Type;
import static logic.genetic.placing.PlacingHeuristics.Type.getForLabel;
import static logic.genetic.placing.PlacingHeuristics.Type.values;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import factory.provider.BottomLeftHeuristicsProvider;
import factory.provider.GreedyPlacingHeuristicsProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.placing.PlacingHeuristics;
import models.dto.CreateComputationDto;

@Singleton
public class PlacingHeuristicsFactory implements Factory<String, PlacingHeuristics> {

  private final BottomLeftHeuristicsProvider bottomLeftHeuristicsProvider;
  private final GreedyPlacingHeuristicsProvider greedyPlacingHeuristicsProvider;

  @Inject
  public PlacingHeuristicsFactory(BottomLeftHeuristicsProvider bottomLeftHeuristicsProvider,
      GreedyPlacingHeuristicsProvider greedyPlacingHeuristicsProvider) {
    this.bottomLeftHeuristicsProvider = bottomLeftHeuristicsProvider;
    this.greedyPlacingHeuristicsProvider = greedyPlacingHeuristicsProvider;
  }


  public PlacingHeuristics create(CreateComputationDto dto)
      throws ImplementationNotFoundException, EntityNotFoundException {
    return create(dto.getGaParameters().getPlacingHeuristics());
  }

  @Override
  public PlacingHeuristics create(String name)
      throws ImplementationNotFoundException, EntityNotFoundException {
    switch (findOrThrow(name)) {
      case BOTTOM_LEFT:
        return bottomLeftHeuristicsProvider.get();
      case GREEDY:
        return greedyPlacingHeuristicsProvider.get();
      default:
        throw new ImplementationNotFoundException(PlacingHeuristics.class, name);
    }
  }

  private Type findOrThrow(String name) throws EntityNotFoundException {
    return getForLabel(name).orElseThrow(
        () -> new EntityNotFoundException(PlacingHeuristics.class, name, values()));
  }
}
