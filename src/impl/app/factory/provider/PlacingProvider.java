package factory.provider;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import logic.genetic.Placing;
import services.RectangleService;

@Singleton
public class PlacingProvider implements Provider<Placing> {

  private final Placing placing;

  @Inject
  public PlacingProvider(
      RectangleService rectangleService, RandomKeyDecoderProvider decoderProvider) {
    this.placing = new Placing(rectangleService, decoderProvider.get());
  }

  @Override
  public Placing get() {
    return placing;
  }
}
