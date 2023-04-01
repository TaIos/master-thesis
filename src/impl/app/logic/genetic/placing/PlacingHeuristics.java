package logic.genetic.placing;

import logic.objective.Objective;
import models.entity.Painting;
import models.entity.PaintingPlacement;
import models.entity.PaintingsFlow;
import models.entity.PlacedSlicingLayout;
import models.entity.Rectangle;
import models.entity.SlicingLayout;

public interface PlacingHeuristics {

  PlacedSlicingLayout place(SlicingLayout slicingLayout, Objective objective, PaintingsFlow flow);

  default PaintingPlacement createPlacement(int x, int y, Painting painting,
      Rectangle allocatedSpace) {
    return PaintingPlacement.builder()
        .painting(painting)
        .allocatedSpace(allocatedSpace)
        .placement(Rectangle.builder()
            .x(x)
            .y(y)
            .width(painting.getWidth())
            .height(painting.getHeight())
            .build())
        .build();
  }
}
