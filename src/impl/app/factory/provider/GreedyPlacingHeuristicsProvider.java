package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.genetic.placing.GreedyPlacingHeuristics;

@Singleton
public class GreedyPlacingHeuristicsProvider implements Provider<GreedyPlacingHeuristics> {

  private final GreedyPlacingHeuristics placingHeuristics;

  public GreedyPlacingHeuristicsProvider() {
    this.placingHeuristics = new GreedyPlacingHeuristics();
  }

  @Override
  public GreedyPlacingHeuristics get() {
    return placingHeuristics;
  }
}
