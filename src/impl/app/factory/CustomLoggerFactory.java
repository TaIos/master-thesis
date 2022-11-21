package factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public final class CustomLoggerFactory implements Factory<String, Logger> {

  @Override
  public Logger create(String name) {
    return LoggerFactory.getLogger(name);
  }

  public Logger create(Class<?> clazz) {
    return org.slf4j.LoggerFactory.getLogger(clazz);
  }
}
