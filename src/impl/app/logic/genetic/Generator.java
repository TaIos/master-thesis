package logic.genetic;

import static utils.JavaUtils.Vector.generateRandomProbabilityVector;
import static utils.JavaUtils.range;

import factory.copy_factory.PaintingCopyFactory;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import models.entity.Individual;
import models.entity.Orientation;
import models.entity.Orientation.Type;
import models.entity.OrientationProbability;
import models.entity.Painting;
import models.entity.RandomIndividualGenerationRequest;

public class Generator {

  private final Random rnd;
  private final PaintingCopyFactory paintingCopyFactory;

  public Generator(Random random, PaintingCopyFactory paintingCopyFactory) {
    rnd = random;
    this.paintingCopyFactory = paintingCopyFactory;
  }

  public Individual random(RandomIndividualGenerationRequest req) {
    int pSize = req.getPaintingSeq().size();
    Individual ind = Individual.builder()
        .paintingSeq(copyPaintingSequence(req))
        .paintingSeqRandomKey(generateRandomProbabilityVector(pSize, rnd))
        .slicingOrderRandomKey(generateRandomProbabilityVector(pSize - 1, rnd))
        .build();
    generateAndSetOrientations(ind, pSize - 1);
    return ind;
  }

  private void generateAndSetOrientations(Individual ind, int size) {
    List<OrientationProbability> probs = generateRandomOrientationProbability(size);
    ind.setOrientationProb(probs);
    ind.setOrientations(probs.stream()
        .map(OrientationProbability::getMostProbable)
        .map(Orientation::new)
        .collect(Collectors.toList()));
  }

  private List<OrientationProbability> generateRandomOrientationProbability(int size) {
    return range(0, size).map(
            dummy -> new OrientationProbability(
                generateRandomProbabilityVector(Type.values().length, rnd)))
        .collect(Collectors.toList());
  }


  private List<Painting> copyPaintingSequence(RandomIndividualGenerationRequest req) {
    return req.getPaintingSeq().stream().map(paintingCopyFactory::createCopy)
        .collect(Collectors.toList());
  }
}
