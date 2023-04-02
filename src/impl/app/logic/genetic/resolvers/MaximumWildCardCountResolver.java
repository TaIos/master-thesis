package logic.genetic.resolvers;

import static models.entity.Orientation.OrientationType.WILD_CARD;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import models.entity.Individual;
import models.entity.Orientation;
import models.entity.OrientationProbability;
import org.apache.commons.lang3.tuple.Pair;

public class MaximumWildCardCountResolver {

  public List<Orientation> resolve(Individual individual, int maximumWildCardCount) {
    List<Pair<Integer, Double>> wildCards = new ArrayList<>();
    List<Orientation> res = resolveAndRecordWildcards(individual, wildCards);
    editWildcards(res, wildCards, individual, maximumWildCardCount);
    return res;
  }

  private List<Orientation> resolveAndRecordWildcards(
      Individual individual,
      List<Pair<Integer, Double>> wildCards) {
    List<Orientation> res = new ArrayList<>(individual.getOrientationProb().size());
    for (int i = 0; i < individual.getOrientationProb().size(); i++) {
      OrientationProbability prob = individual.getOrientationProb().get(i);
      Orientation orientation = new Orientation(prob.getMostProbable());
      if (orientation.getType().equals(WILD_CARD)) {
        wildCards.add(Pair.of(i, prob.getProbabilityVector().get(2)));
      }
      res.add(orientation);
    }
    return res;
  }

  private void editWildcards(
      List<Orientation> res,
      List<Pair<Integer, Double>> wildCards,
      Individual individual,
      int maximumWildCardCount) {
    if (wildCards.size() <= maximumWildCardCount) {
      return;
    }
    wildCards.sort(Comparator.comparingDouble(Pair::getRight));
    for (int i = 0; i < wildCards.size() - maximumWildCardCount; i++) {
      int idx = wildCards.get(i).getLeft();
      res.set(idx, new Orientation(
          individual.getOrientationProb().get(idx).getMostProbableWithoutWildcard()));
    }
  }

}
