package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class JavaUtils {

  private JavaUtils() {
    throw new UnsupportedOperationException(
        "This class is only collection of static method and should not be instantiated.");
  }

  public static <T> List<T> shuffle(List<T> list, Random rnd) {
    Collections.shuffle(list, rnd);
    return list;
  }

  public static Stream<Integer> range(int startInclusive, int endExclusive) {
    return IntStream.range(startInclusive, endExclusive).boxed();
  }

  public static <T extends EnumTypeInterface> Optional<T> getForLabel(T[] values, String label) {
    return Arrays.stream(values).filter(val -> val.getLabel().equals(label)).findFirst();
  }

  public static String formatAsHumanReadableDuration(Long duration, TimeUnit timeUnit) {
    // credit goes to joseluisbz: https://stackoverflow.com/a/61449373/6784881
    long intMillis = timeUnit.toMillis(duration);
    long dd = timeUnit.toDays(intMillis);
    intMillis -= TimeUnit.DAYS.toMillis(dd);
    long hh = TimeUnit.MILLISECONDS.toHours(intMillis);
    intMillis -= TimeUnit.HOURS.toMillis(hh);
    long mm = TimeUnit.MILLISECONDS.toMinutes(intMillis);
    intMillis -= TimeUnit.MINUTES.toMillis(mm);
    long ss = TimeUnit.MILLISECONDS.toSeconds(intMillis);
    intMillis -= TimeUnit.SECONDS.toMillis(ss);

    String stringInterval = "%02d days, %02d hours, %02d minutes, %02d.%03d seconds";
    return String.format(stringInterval, dd, hh, mm, ss, intMillis);
  }

  public static <T> List<T> concatWithPreserveOrder(List<T> first, List<T> second) {
    List<T> result = new ArrayList<>(first.size() + second.size());
    result.addAll(first);
    result.addAll(second);
    return result;
  }
}
