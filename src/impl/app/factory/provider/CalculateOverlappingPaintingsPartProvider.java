package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.objective.parts.OverlappingRectanglesCount;

@Singleton
public class CalculateOverlappingPaintingsPartProvider implements
    Provider<OverlappingRectanglesCount> {

  private final OverlappingRectanglesCount calculateOverlappingPaintings;

  public CalculateOverlappingPaintingsPartProvider() {
    calculateOverlappingPaintings = new OverlappingRectanglesCount();
  }

  @Override
  public OverlappingRectanglesCount get() {
    return calculateOverlappingPaintings;
  }
}
