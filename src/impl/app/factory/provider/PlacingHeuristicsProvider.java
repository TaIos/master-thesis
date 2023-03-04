package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.genetic.algorithm.PlacingHeuristics;

@Singleton
public class PlacingHeuristicsProvider implements Provider<PlacingHeuristics> {

  private final PlacingHeuristics placingHeuristics;

  public PlacingHeuristicsProvider() {
    this.placingHeuristics = new PlacingHeuristics();
  }

  @Override
  public PlacingHeuristics get() {
    return placingHeuristics;
  }
}
