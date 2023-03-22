package logic.genetic.placing;

import static utils.JavaUtils.createPairs;

import java.util.stream.Collectors;
import logic.objective.Objective;
import logic.objective.ObjectiveValueComparator;
import models.entity.Painting;
import models.entity.PaintingPlacement;
import models.entity.PlacedSlicingLayout;
import models.entity.Rectangle;
import models.entity.SlicingLayout;

public class GreedyPlacingHeuristics implements PlacingHeuristics {


  @Override
  public PlacedSlicingLayout place(SlicingLayout slicingLayout, Objective objective) {
    return new PlacedSlicingLayout(
        createPairs(slicingLayout.getPaintings(), slicingLayout.getAllocatedSpace())
            .parallelStream()
            .map(pair -> place(pair.getLeft(), pair.getRight(), objective))
            .collect(Collectors.toList()));
  }


  /**
   * try all points and return best
   */
  private PaintingPlacement place(Painting painting, Rectangle allocatedSpace,
      Objective objective) {
    PaintingPlacement bestPlacement = createPlacement(allocatedSpace.getX(), allocatedSpace.getY(),
        painting, allocatedSpace);
    double bestObj = ObjectiveValueComparator.OBJECTIVE_VALUE_MAX;

    for (int x = allocatedSpace.getX(); x < allocatedSpace.getX() + allocatedSpace.getWidth();
        x++) {
      for (int y = allocatedSpace.getY(); y < allocatedSpace.getY() + allocatedSpace.getHeight();
          y++) {
        PaintingPlacement placement = createPlacement(x, y, painting, allocatedSpace);
        double obj = objective.eval(placement);
        if (obj < bestObj) {
          bestObj = obj;
          bestPlacement = placement;
        }
      }
    }

    return bestPlacement;
  }


}
