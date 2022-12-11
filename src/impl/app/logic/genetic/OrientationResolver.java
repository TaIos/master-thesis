package logic.genetic;

import static models.entity.Orientation.Type.HORIZONTAL;
import static models.entity.Orientation.Type.VERTICAL;
import static models.entity.Orientation.Type.WILD_CARD;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import models.entity.Orientation;

@Singleton
public class OrientationResolver {

  public List<List<Orientation>> resolve(List<Orientation> orientations) {
    return resolveRecursive(orientations, new ArrayList<>(), 0);
  }

  private List<List<Orientation>> resolveRecursive(final List<Orientation> orientations,
      List<Orientation> currResolved, int idx) {
    if (idx == orientations.size()) {
      return new ArrayList<>(List.of(currResolved));
    }
    if (!orientations.get(idx).getType().equals(WILD_CARD)) {
      currResolved.add(orientations.get(idx));
      return resolveRecursive(orientations, currResolved, idx + 1);
    } else {
      var horizontal = new ArrayList<>(currResolved);
      horizontal.add(new Orientation(HORIZONTAL));
      var vertical = new ArrayList<>(currResolved);
      vertical.add(new Orientation(VERTICAL));
      List<List<Orientation>> res = resolveRecursive(orientations, horizontal, idx + 1);
      res.addAll(resolveRecursive(orientations, vertical, idx + 1));
      return res;
    }
  }
}
