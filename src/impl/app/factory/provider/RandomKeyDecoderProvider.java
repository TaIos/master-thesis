package factory.provider;

import logic.genetic.RandomKeyDecoder;

import javax.inject.Provider;
import javax.inject.Singleton;

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
