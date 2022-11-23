package factory.provider;

import logic.genetic.Generator;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class GeneratorProvider implements Provider<Generator> {
  private final Generator generator;

  @Inject
  public GeneratorProvider(RandomProvider randomProvider) {
    this.generator = new Generator(randomProvider.get());
  }

  @Override
  public Generator get() {
    return generator;
  }
}
