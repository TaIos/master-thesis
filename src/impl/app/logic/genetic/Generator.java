package logic.genetic;

import models.entity.Facility;
import models.entity.Individual;
import models.entity.Orientation;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static utils.JavaUtils.range;
import static utils.JavaUtils.shuffle;

public class Generator {
  private final Random rnd;

  public Generator() {
    rnd = new Random(System.currentTimeMillis());
  }

  public Individual randomAutoIdent(int facilityCount) {
    return random(range(facilityCount).map(Object::toString).collect(Collectors.toList()));
  }

  public Individual random(List<String> facilityIdents) {
    return new Individual(
        generateRandomFacilitySequence(facilityIdents),
        generateRandomSlicingOrder(facilityIdents.size()),
        generateRandomOrientations(facilityIdents.size()));
  }

  private List<Facility> generateRandomFacilitySequence(List<String> facilityIdents) {
    return shuffle(
        facilityIdents.stream()
            .map(ident -> new Facility(ident, null))
            .collect(Collectors.toList()),
        rnd);
  }

  private List<Integer> generateRandomSlicingOrder(int size) {
    return shuffle(range(size).collect(Collectors.toList()), rnd);
  }

  private List<Orientation> generateRandomOrientations(int size) {
    return range(size)
        .map(dummy -> new Orientation(generateRandomOrientationType()))
        .collect(Collectors.toList());
  }

  private Orientation.Type generateRandomOrientationType() {
    return Orientation.Type.values()[rnd.nextInt(Orientation.Type.values().length)];
  }
}
