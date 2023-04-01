package logic.genetic.placing;

import java.util.ArrayList;
import logic.objective.Objective;
import logic.objective.ObjectiveValueComparator;
import models.entity.Painting;
import models.entity.PaintingPlacement;
import models.entity.PaintingsFlow;
import models.entity.PlacedSlicingLayout;
import models.entity.Rectangle;
import models.entity.SlicingLayout;

public class GreedyPlacingHeuristics implements PlacingHeuristics {


  @Override
  public PlacedSlicingLayout place(SlicingLayout slicingLayout,
      Objective objective,
      PaintingsFlow flow) {
    assert (slicingLayout.getAllocatedSpace().size() == slicingLayout.getPaintings().size());
    PlacedSlicingLayout placedSlicingLayout = new PlacedSlicingLayout(
        new ArrayList<>(slicingLayout.getPaintings().size()));

    for (int i = 0; i < slicingLayout.getPaintings().size(); i++) {
      place(slicingLayout.getPaintings().get(i),
          slicingLayout.getAllocatedSpace().get(i),
          placedSlicingLayout,
          objective,
          flow);
    }

    return placedSlicingLayout;
  }


  /**
   * try all points and return best
   */
  private void place(Painting painting,
      Rectangle allocatedSpace,
      PlacedSlicingLayout placedSlicingLayout,
      Objective objective,
      PaintingsFlow flow) {

    PaintingPlacement bestPlacement = createPlacement(allocatedSpace.getX(), allocatedSpace.getY(),
        painting, allocatedSpace);
    double bestObj = ObjectiveValueComparator.OBJECTIVE_VALUE_MAX;

    for (int x = allocatedSpace.getX();
        x + painting.getWidth() <= allocatedSpace.getX() + allocatedSpace.getWidth();
        x++) {
      for (int y = allocatedSpace.getY();
          y + painting.getHeight() <= allocatedSpace.getY() + allocatedSpace.getHeight();
          y++) {
        PaintingPlacement placement = createPlacement(x, y, painting, allocatedSpace);
        double obj = objective.peek(placedSlicingLayout, placement, flow);

        if (obj < bestObj) {
          bestObj = obj;
          bestPlacement = placement;
        }
      }
    }

    placedSlicingLayout.getPlacements().add(bestPlacement);
  }


}
