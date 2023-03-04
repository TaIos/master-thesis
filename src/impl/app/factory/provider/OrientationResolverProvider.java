package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.genetic.resolvers.OrientationResolver;

@Singleton
public class OrientationResolverProvider implements
    Provider<OrientationResolver> {

  private final OrientationResolver orientationResolver;

  public OrientationResolverProvider() {
    orientationResolver = new OrientationResolver();
  }

  @Override
  public OrientationResolver get() {
    return orientationResolver;
  }
}
