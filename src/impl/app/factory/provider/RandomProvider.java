package factory.provider;

import com.typesafe.config.Config;
import java.util.Random;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class RandomProvider implements Provider<Random> {

  private final Random random;

  @Inject
  public RandomProvider(Config config) {
    this(new Random(
        config.getBoolean("useSeed")
            ? config.getLong("randomSeed")
            : System.currentTimeMillis()));
  }

  public RandomProvider(Random random) {
    this.random = random;
  }

  @Override
  public Random get() {
    return random;
  }
}
