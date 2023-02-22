package factory.provider;

import factory.copy_factory.RectangleCopyFactory;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class RectangleCopyFactoryProvider implements Provider<RectangleCopyFactory> {

  private final RectangleCopyFactory rectangleCopyFactory;

  @Inject
  public RectangleCopyFactoryProvider(RectangleCopyFactory rectangleCopyFactory) {
    this.rectangleCopyFactory = rectangleCopyFactory;
  }

  @Override
  public RectangleCopyFactory get() {
    return rectangleCopyFactory;
  }
}
