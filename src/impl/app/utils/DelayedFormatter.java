package utils;

/**
 * credit: Svullo from <a href="https://stackoverflow.com/a/42531620/6784881">...</a>
 */
public class DelayedFormatter {

  public static Object format(String format, Object... args) {
    return new Object() {
      @Override
      public String toString() {
        return String.format(format, args);
      }
    };
  }
}

