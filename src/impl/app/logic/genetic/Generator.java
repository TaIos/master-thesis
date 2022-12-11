package logic.genetic;

import static utils.JavaUtils.range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import models.entity.Individual;
import models.entity.Orientation;
import models.entity.Orientation.Type;
import models.entity.Painting;
import models.entity.RandomIndividualGenerationRequest;

public class Generator {

  private final Random rnd;

  public Generator(Random random) {
    rnd = random;
  }

  public Individual random(RandomIndividualGenerationRequest req) {
    int pSize = req.getPaintingSeq().size();
    return Individual.builder()
        .paintingSeq(
            req.getPaintingSeq().stream().map(Painting::clone).collect(Collectors.toList()))
        .paintingSeqRandomKey(generateRandomKeys(pSize))
        .slicingOrderRandomKey(generateRandomKeys(pSize - 1))
        .orientations(generateRandomOrientations(pSize - 1, req.getMaximumWildCardCount()))
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

  private List<Orientation> generateRandomOrientations(int size, int maximumWildCardCount) {
    List<Orientation> orientations = generateRandomOrientations(size);
    List<Integer> wildCardIndices = new ArrayList<>(orientations.size() / 3);
    for (int i = 0; i < orientations.size(); i++) {
      if (orientations.get(i).getType().equals(Type.WILD_CARD)) {
        wildCardIndices.add(i);
      }
    }
    int toRemoveCnt = wildCardIndices.size() - maximumWildCardCount;
    if (toRemoveCnt > 0) {
      Collections.shuffle(wildCardIndices, rnd);
      for (int i = 0; i < toRemoveCnt; i++) {
        orientations.get(wildCardIndices.get(i))
            .setType(Orientation.Type.getRandomWithoutWildCard(rnd));
      }
    }
    return orientations;
  }
}
