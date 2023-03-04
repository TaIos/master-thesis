package logic.genetic.algorithm;

import java.util.ArrayList;
import java.util.List;
import models.entity.Painting;
import models.entity.PaintingPlacement;
import models.entity.PlacedSlicingLayout;
import models.entity.Rectangle;
import models.entity.SlicingLayout;

public class PlacingHeuristics {

  public PlacedSlicingLayout place(SlicingLayout slicingLayout) {
    return PlacedSlicingLayout.builder()
        .placements(create(slicingLayout))
        .build();
  }

  // TODO create something smarter if there is more time
  private List<PaintingPlacement> create(SlicingLayout slicingLayout) {
    int size = slicingLayout.getPaintings().size();
    List<PaintingPlacement> placements = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      Painting p = slicingLayout.getPaintings().get(i);
      Rectangle rec = slicingLayout.getAllocatedSpace().get(i);
      placements.add(PaintingPlacement.builder()
          .allocatedSpace(rec)
          .placement(Rectangle.builder()
              .x(rec.getX())
              .y(rec.getY())
              .width(p.getWidth())
              .height(p.getHeight())
              .build())
          .build());
    }
    return placements;
  }

}
