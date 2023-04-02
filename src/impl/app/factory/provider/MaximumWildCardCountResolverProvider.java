package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.genetic.resolvers.MaximumWildCardCountResolver;

@Singleton
public class MaximumWildCardCountResolverProvider implements
    Provider<MaximumWildCardCountResolver> {

  private final MaximumWildCardCountResolver maximumWildCardCountResolver;


  public MaximumWildCardCountResolverProvider() {
    this.maximumWildCardCountResolver = new MaximumWildCardCountResolver();
  }

  @Override
  public MaximumWildCardCountResolver get() {
    return maximumWildCardCountResolver;
  }
}
