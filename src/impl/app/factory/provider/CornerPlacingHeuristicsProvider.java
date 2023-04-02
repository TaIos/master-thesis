package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.genetic.placing.BottomLeftPlacingHeuristics;
import logic.genetic.placing.CornerPlacingHeuristics;

@Singleton
public class CornerPlacingHeuristicsProvider implements Provider<CornerPlacingHeuristics> {

  private final CornerPlacingHeuristics cornerPlacingHeuristics;

  public CornerPlacingHeuristicsProvider() {
    this.cornerPlacingHeuristics = new CornerPlacingHeuristics();
  }

  @Override
  public CornerPlacingHeuristics get() {
    return cornerPlacingHeuristics;
  }
}
