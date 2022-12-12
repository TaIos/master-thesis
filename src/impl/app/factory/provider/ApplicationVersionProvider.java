package factory.provider;

import com.typesafe.config.Config;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class ApplicationVersionProvider implements Provider<String> {

  private final String applicationVersion;

  @Inject
  public ApplicationVersionProvider(Config config) {
    this.applicationVersion = config.getString("version");
  }

  public String get() {
    return applicationVersion;
  }
}
