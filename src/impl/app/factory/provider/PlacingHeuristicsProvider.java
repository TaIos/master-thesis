package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.genetic.placing.GreedyPlacingHeuristics;

@Singleton
public class PlacingHeuristicsProvider implements Provider<GreedyPlacingHeuristics> {

  private final GreedyPlacingHeuristics placingHeuristics;

  public PlacingHeuristicsProvider() {
    this.placingHeuristics = new GreedyPlacingHeuristics();
  }

  @Override
  public GreedyPlacingHeuristics get() {
    return placingHeuristics;
  }
}
