package factory.provider;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import logic.genetic.Generator;

@Singleton
public class GeneratorProvider implements Provider<Generator> {

  private final Generator generator;

  @Inject
  public GeneratorProvider(RandomProvider randomProvider,
      PaintingCopyFactoryProvider paintingCopyFactoryProvider) {
    this.generator = new Generator(randomProvider.get(), paintingCopyFactoryProvider.get());
  }

  @Override
  public Generator get() {
    return generator;
  }
}
