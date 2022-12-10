package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.genetic.RandomKeyDecoder;

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
