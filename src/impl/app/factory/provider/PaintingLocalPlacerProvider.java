package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.placing.PaintingLocalPlacer;

@Singleton
public class PaintingLocalPlacerProvider implements Provider<PaintingLocalPlacer> {

  private final PaintingLocalPlacer paintingLocalPlacer;

  public PaintingLocalPlacerProvider() {
    paintingLocalPlacer = new PaintingLocalPlacer();
  }

  @Override
  public PaintingLocalPlacer get() {
    return paintingLocalPlacer;
  }
}
