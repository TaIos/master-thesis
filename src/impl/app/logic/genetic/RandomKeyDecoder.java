package logic.genetic;

import akka.japi.Pair;
import models.entity.Facility;
import models.entity.Individual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static utils.JavaUtils.range;

public class RandomKeyDecoder {

  public List<Facility> decodeFacilitySequence(Individual ind) {
    return decode(ind.getFacilitySequence(), ind.getFacilitySequenceRandomKey());
  }

  public List<Integer> decodeSlicingOrder(Individual ind) {
    return decode(
        range(1, ind.getSlicingOrderRandomKey().size() + 1).collect(Collectors.toList()),
        ind.getSlicingOrderRandomKey());
  }

  public <T> List<T> decode(List<T> values, List<Double> randomKeys) {
    throwIfInvalidSizes(values, randomKeys);
    List<Pair<Integer, Double>> pairs = new ArrayList<>(randomKeys.size());
    for (int i = 0; i < randomKeys.size(); i++) {
      pairs.add(new Pair<>(i, randomKeys.get(i)));
    }
    pairs.sort(Comparator.comparing(Pair::second));

    List<T> result = new ArrayList<>(values.size());
    for (int i = 0; i < values.size(); i++) {
      result.add(values.get(pairs.get(i).first()));
    }
    return result;
  }

  private <T> void throwIfInvalidSizes(List<T> values, List<Double> randomKeys) {
    if (values.size() != randomKeys.size()) {
      throw new IllegalArgumentException(
          String.format(
              "%s: input sizes differ – values=[%s], randomKeys=[%s]",
              RandomKeyDecoder.class.getSimpleName(), values.size(), randomKeys.size()));
    }
  }
}
