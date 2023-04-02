package logic.genetic.placing;

import static logic.genetic.placing.PlacingHeuristics.Type.BOTTOM_LEFT;

import java.util.List;
import models.entity.Painting;
import models.entity.Point;
import models.entity.Rectangle;

public class BottomLeftPlacingHeuristics extends BasePlacingHeuristics {

  @Override
  public List<Point> createPointsToTry(Painting painting, Rectangle allocatedSpace) {
    return List.of(
        Point.builder()
            .x(allocatedSpace.getX())
            .y(allocatedSpace.getY())
            .build()
    );
  }

  @Override
  public Type getType() {
    return BOTTOM_LEFT;
  }
}
