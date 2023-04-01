package utils;

import com.google.common.collect.Collections2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;

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

  public static <T> List<T> concatWithPreservedOrder(List<T> first, List<T> second) {
    List<T> result = new ArrayList<>(first.size() + second.size());
    result.addAll(first);
    result.addAll(second);
    return result;
  }

  public static <T> List<List<T>> permutations(List<T> lst) {
    System.out.println("SIZE=" + lst.size());
    List<List<T>> res = new ArrayList<>(Collections2.permutations(lst));
    System.out.println("OK");
    return res;
  }

  public static <A, B> List<Pair<A, B>> createPairs(List<A> a, List<B> b) {
    assert (a.size() == b.size());
    List<Pair<A, B>> pairs = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) {
      pairs.add(Pair.of(a.get(i), b.get(i)));
    }
    return pairs;
  }

  public static int toInteger(Boolean bool) {
    return bool ? 1 : 0;
  }

  /**
   * @param lst list containing exactly 3 elements
   * @return index of the greatest value
   */
  public static int getMax3Index(List<Double> lst) {
    int indexOfMax = 0;
    if (lst.get(1) > lst.get(0)) {
      indexOfMax = 1;
    }
    if (lst.get(2) > lst.get(indexOfMax)) {
      indexOfMax = 2;
    }
    return indexOfMax;
  }

  public static class Vector {

    public static List<Double> sumVector(List<Double> first, List<Double> second) {
      List<Double> res = new ArrayList<>(first.size());
      for (int i = 0; i < first.size(); i++) {
        res.add(first.get(i) + second.get(i));
      }
      return res;
    }

    public static List<Double> normalizeToProbabilityVector(List<Double> lst) {
      List<Double> copy = new ArrayList<>(lst);
      normalizeToProbabilityVectorInplace(copy);
      return copy;
    }

    public static void normalizeToProbabilityVectorInplace(List<Double> lst) {
      double sum = lst.stream().mapToDouble(Double::doubleValue).sum();
      lst.replaceAll(elem -> elem / sum);
    }


    public static List<Double> multVector(List<Double> vec, double weight) {
      List<Double> res = new ArrayList<>(vec.size());
      for (Double elem : vec) {
        res.add(elem * weight);
      }
      return res;
    }

    public static List<Double> multVector(List<Double> vec, List<Double> weights) {
      List<Double> res = new ArrayList<>(vec.size());
      for (int i = 0; i < vec.size(); i++) {
        res.add(vec.get(i) * weights.get(i));
      }
      return res;
    }
  }
}
