package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.genetic.resolvers.RandomKeyDecoder;

@Singleton
public class RandomKeyDecoderProvider implements Provider<RandomKeyDecoder> {

  private final RandomKeyDecoder decoder;

  public RandomKeyDecoderProvider() {
    decoder = new RandomKeyDecoder();
  }

  @Override
  public RandomKeyDecoder get() {
    return decoder;
  }
}
