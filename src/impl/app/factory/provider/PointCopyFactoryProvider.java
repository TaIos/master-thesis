package factory.provider;

import factory.copy_factory.PointCopyFactory;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class PointCopyFactoryProvider implements Provider<PointCopyFactory> {

  private final PointCopyFactory pointCopyFactory;

  @Inject
  public PointCopyFactoryProvider(PointCopyFactory pointCopyFactory) {
    this.pointCopyFactory = pointCopyFactory;
  }

  @Override
  public PointCopyFactory get() {
    return pointCopyFactory;
  }
}
