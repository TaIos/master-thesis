package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.genetic.placing.BottomLeftPlacingHeuristics;

@Singleton
public class BottomLeftHeuristicsProvider implements Provider<BottomLeftPlacingHeuristics> {

  private final BottomLeftPlacingHeuristics bottomLeftPlacingHeuristics;

  public BottomLeftHeuristicsProvider() {
    this.bottomLeftPlacingHeuristics = new BottomLeftPlacingHeuristics();
  }

  @Override
  public BottomLeftPlacingHeuristics get() {
    return bottomLeftPlacingHeuristics;
  }
}
