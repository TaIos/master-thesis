package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.objective.parts.global.CalculateOverlappingPaintings;

@Singleton
public class CalculateOverlappingPaintingsPartProvider implements
    Provider<CalculateOverlappingPaintings> {

  private final CalculateOverlappingPaintings calculateOverlappingPaintings;

  public CalculateOverlappingPaintingsPartProvider() {
    calculateOverlappingPaintings = new CalculateOverlappingPaintings();
  }

  @Override
  public CalculateOverlappingPaintings get() {
    return calculateOverlappingPaintings;
  }
}
