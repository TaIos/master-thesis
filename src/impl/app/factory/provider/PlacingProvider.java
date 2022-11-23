package factory.provider;

import logic.genetic.Placing;
import services.RectangleService;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class PlacingProvider implements Provider<Placing> {

  private final Placing placing;

  @Inject
  public PlacingProvider(RectangleService rectangleService) {
    this.placing = new Placing(rectangleService);
  }

  @Override
  public Placing get() {
    return placing;
  }
}
