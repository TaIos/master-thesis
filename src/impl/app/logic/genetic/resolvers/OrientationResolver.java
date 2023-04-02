package logic.genetic.resolvers;

import static models.entity.Orientation.OrientationType.WILD_CARD;

import java.util.ArrayList;
import java.util.List;
import models.entity.Orientation;
import models.entity.Orientation.OrientationType;
import models.entity.ResolvedOrientation;
import models.entity.ResolvedOrientation.ResolvedOrientationType;

public class OrientationResolver {

  public List<List<ResolvedOrientation>> resolveAllPossibleCombinationsFromProbs(
      List<Orientation> probs) {
    return resolveRecursive(probs, new ArrayList<>(), 0);
  }

  private List<List<ResolvedOrientation>> resolveRecursive(
      List<Orientation> orientations,
      List<ResolvedOrientation> currResolved,
      int idx) {
    if (idx == orientations.size()) {
      return new ArrayList<>(List.of(currResolved));
    }
    if (!orientations.get(idx).getType().equals(WILD_CARD)) {
      currResolved.add(new ResolvedOrientation(transform(orientations.get(idx).getType())));
      return resolveRecursive(orientations, currResolved, idx + 1);
    } else {
      var horizontal = new ArrayList<>(currResolved);
      horizontal.add(new ResolvedOrientation(ResolvedOrientationType.HORIZONTAL));
      var vertical = new ArrayList<>(currResolved);
      vertical.add(new ResolvedOrientation(ResolvedOrientationType.VERTICAL));
      List<List<ResolvedOrientation>> res = resolveRecursive(orientations, horizontal, idx + 1);
      res.addAll(resolveRecursive(orientations, vertical, idx + 1));
      return res;
    }
  }

  private ResolvedOrientationType transform(OrientationType orientationType) {
    switch (orientationType) {
      case HORIZONTAL:
        return ResolvedOrientationType.HORIZONTAL;
      case VERTICAL:
        return ResolvedOrientationType.VERTICAL;
      case WILD_CARD:
      default:
        throw new UnsupportedOperationException("Can't transform wild card");
    }
  }
}
