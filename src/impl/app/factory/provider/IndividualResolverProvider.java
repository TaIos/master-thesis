package factory.provider;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import logic.genetic.resolvers.IndividualResolver;

@Singleton
public class IndividualResolverProvider implements Provider<IndividualResolver> {

  private final IndividualResolver individualResolver;

  @Inject
  public IndividualResolverProvider(
      OrientationResolverProvider orientationResolverProvider,
      RandomKeyDecoderProvider randomKeyDecoderProvider,
      MaximumWildCardCountResolverProvider maximumWildCardCountResolverProvider) {
    this.individualResolver = new IndividualResolver(
        orientationResolverProvider.get(),
        maximumWildCardCountResolverProvider.get(),
        randomKeyDecoderProvider.get());
  }

  @Override
  public IndividualResolver get() {
    return individualResolver;
  }
}
