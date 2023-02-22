package factory.provider;

import factory.copy_factory.PaintingCopyFactory;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class PaintingCopyFactoryProvider implements Provider<PaintingCopyFactory> {

  private final PaintingCopyFactory paintingCopyFactory;

  @Inject
  public PaintingCopyFactoryProvider(PaintingCopyFactory paintingCopyFactory) {
    this.paintingCopyFactory = paintingCopyFactory;
  }

  @Override
  public PaintingCopyFactory get() {
    return paintingCopyFactory;
  }
}
