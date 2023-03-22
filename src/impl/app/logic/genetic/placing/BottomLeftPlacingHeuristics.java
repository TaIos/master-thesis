package logic.genetic.placing;

import java.util.ArrayList;
import java.util.List;
import logic.objective.Objective;
import models.entity.PaintingPlacement;
import models.entity.PlacedSlicingLayout;
import models.entity.Rectangle;
import models.entity.SlicingLayout;

public class BottomLeftPlacingHeuristics implements PlacingHeuristics {

  @Override
  public PlacedSlicingLayout place(SlicingLayout slicingLayout, Objective objective) {
    return PlacedSlicingLayout.builder()
        .placements(createBottomLeftPlacements(slicingLayout))
        .build();
  }

  private List<PaintingPlacement> createBottomLeftPlacements(SlicingLayout slicingLayout) {
    assert (slicingLayout.getAllocatedSpace().size() == slicingLayout.getPaintings().size());
    List<PaintingPlacement> placements = new ArrayList<>(slicingLayout.getPaintings().size());
    for (int i = 0; i < slicingLayout.getPaintings().size(); i++) {
      Rectangle allocatedSpace = slicingLayout.getAllocatedSpace().get(i);
      placements.add(
          createPlacement(
              allocatedSpace.getX(),
              allocatedSpace.getY(),
              slicingLayout.getPaintings().get(i),
              allocatedSpace));
    }
    return placements;
  }
}
