package factory.provider;

import factory.BestIndividualFactory;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class BestIndividualFactoryProvider implements Provider<BestIndividualFactory> {

  private final BestIndividualFactory bestIndividualFactory;

  @Inject
  public BestIndividualFactoryProvider(BestIndividualFactory bestIndividualFactory) {
    this.bestIndividualFactory = bestIndividualFactory;
  }

  @Override
  public BestIndividualFactory get() {
    return bestIndividualFactory;
  }
}
