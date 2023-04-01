package logic.genetic.placing;

import static logic.genetic.placing.PlacingHeuristics.Type.BOTTOM_LEFT;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import logic.objective.Objective;
import models.entity.PaintingPlacement;
import models.entity.PaintingsFlow;
import models.entity.PlacedSlicingLayout;
import models.entity.Rectangle;
import models.entity.SlicingLayout;

public class BottomLeftPlacingHeuristics implements PlacingHeuristics {

  @Override
  public PlacedSlicingLayout place(SlicingLayout slicingLayout,
      @Nullable Objective objective,
      @Nullable PaintingsFlow flow) {
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

  @Override
  public Type getType() {
    return BOTTOM_LEFT;
  }
}
