package logic.genetic;

import models.entity.Facility;
import models.entity.Individual;
import models.entity.Orientation;
import models.entity.RandomIndividualGenerationRequest;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static utils.JavaUtils.range;

public class Generator {
  private final Random rnd;

  public Generator(Random random) {
    rnd = random;
  }

  public Individual random(RandomIndividualGenerationRequest req) {
    int fSize = req.getFacilitySequence().size();
    return Individual.builder()
        .facilitySequence(
            req.getFacilitySequence().stream().map(Facility::clone).collect(Collectors.toList()))
        .facilitySequenceRandomKey(generateRandomKeys(fSize))
        .slicingOrderRandomKey(generateRandomKeys(fSize - 1))
        .orientations(generateRandomOrientations(fSize - 1))
        .build();
  }

  private List<Double> generateRandomKeys(int size) {
    return range(0, size).map(dummy -> rnd.nextDouble()).collect(Collectors.toList());
  }

  private List<Orientation> generateRandomOrientations(int size) {
    return range(0, size)
        .map(dummy -> new Orientation(Orientation.Type.getRandom(rnd)))
        .collect(Collectors.toList());
  }
}
