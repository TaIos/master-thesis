package logic.genetic;

import static utils.JavaUtils.Vector.normalizeToProbabilityVector;
import static utils.JavaUtils.range;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import models.entity.Individual;
import models.entity.Orientation.OrientationType;
import models.entity.OrientationProbability;
import models.entity.Painting;

public class Generator {

  private final Random rnd;

  public Generator(Random random) {
    rnd = random;
  }

  public List<Individual> generateRandomIndividualList(List<Painting> paintings, int size) {
    List<Individual> res = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      res.add(generateRandomIndividual(paintings));
    }
    return res;
  }

  public Individual generateRandomIndividual(List<Painting> paintings) {
    return Individual.builder()
        .paintingSeq(paintings)
        .paintingSeqRandomKey(generateRandomProbabilityVector(paintings.size()))
        .slicingOrderRandomKey(generateRandomProbabilityVector(paintings.size() - 1))
        .orientationProb(generateRandomOrientationProbability(paintings.size() - 1))
        .build();
  }

  private List<Double> generateRandomProbabilityVector(int size) {
    List<Double> res = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      res.add(rnd.nextDouble());
    }
    return normalizeToProbabilityVector(res);
  }

  private List<OrientationProbability> generateRandomOrientationProbability(int size) {
    return range(0, size).map(
            dummy -> new OrientationProbability(
                generateRandomProbabilityVector(OrientationType.values().length)))
        .collect(Collectors.toList());
  }


}
