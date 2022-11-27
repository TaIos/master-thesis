package logic.genetic;

import models.entity.Facility;
import models.entity.Individual;
import models.entity.Orientation;
import models.entity.RandomIndividualGenerationRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static utils.JavaUtils.range;
import static utils.JavaUtils.shuffle;

public class Generator {
  private final Random rnd;

  public Generator(Random random) {
    rnd = random;
  }

  public Individual random(RandomIndividualGenerationRequest req) {
    return new Individual(
        new ArrayList<>(
            req.getFacilitySequence().stream().map(Facility::clone).collect(Collectors.toList())),
        generateRandomSlicingOrder(req.getFacilitySequence().size()),
        generateRandomOrientations(req.getFacilitySequence().size()));
  }

  private List<Integer> generateRandomSlicingOrder(int size) {
    return shuffle(range(1, size).collect(Collectors.toList()), rnd);
  }

  private List<Orientation> generateRandomOrientations(int size) {
    return range(1, size)
        .map(dummy -> new Orientation(generateRandomOrientationType()))
        .collect(Collectors.toList());
  }

  private Orientation.Type generateRandomOrientationType() {
    return Orientation.Type.values()[rnd.nextInt(Orientation.Type.values().length)];
  }
}
