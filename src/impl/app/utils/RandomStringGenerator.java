package utils;

import java.util.UUID;
import javax.inject.Singleton;

@Singleton
public class RandomStringGenerator {

  public static int DEFAULT_LENGTH = 5;

  public String generate() {
    return generate(DEFAULT_LENGTH);
  }

  public String generate(int length) {
    String str = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    return str.substring(0, Math.min(length, str.length()));
  }
}
