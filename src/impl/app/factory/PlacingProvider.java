package factory;

import logic.genetic.Placing;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class PlacingProvider implements Provider<Placing> {

  private final Placing placing;

  @Inject
  public PlacingProvider(Placing placing) {
    this.placing = placing;
  }

  @Override
  public Placing get() {
    return placing;
  }
}
