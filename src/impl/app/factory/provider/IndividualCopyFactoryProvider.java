package factory.provider;

import factory.copy_factory.IndividualCopyFactory;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class IndividualCopyFactoryProvider implements Provider<IndividualCopyFactory> {

  private final IndividualCopyFactory individualCopyFactory;

  @Inject
  public IndividualCopyFactoryProvider(IndividualCopyFactory individualCopyFactory) {
    this.individualCopyFactory = individualCopyFactory;
  }

  @Override
  public IndividualCopyFactory get() {
    return individualCopyFactory;
  }
}
