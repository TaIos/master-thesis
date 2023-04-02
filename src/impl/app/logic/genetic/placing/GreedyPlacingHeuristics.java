package logic.genetic.placing;

import static logic.genetic.placing.PlacingHeuristics.Type.GREEDY;

import java.util.ArrayList;
import java.util.List;
import models.entity.Painting;
import models.entity.Point;
import models.entity.Rectangle;

public class GreedyPlacingHeuristics extends BasePlacingHeuristics {


  /**
   * try all points
   */
  @Override
  protected List<Point> createPointsToTry(Painting painting, Rectangle allocatedSpace) {
    List<Point> pointsToTry = new ArrayList<>();
    for (int x = allocatedSpace.getX();
        x + painting.getWidth() <= allocatedSpace.getX() + allocatedSpace.getWidth(); x++) {
      for (int y = allocatedSpace.getY();
          y + painting.getHeight() <= allocatedSpace.getY() + allocatedSpace.getHeight(); y++) {
        pointsToTry.add(Point.builder().x(x).y(y).build());
      }
    }

    addBottomLeftIfEmpty(pointsToTry, allocatedSpace);
    return pointsToTry;
  }

  private void addBottomLeftIfEmpty(List<Point> pointsToTry, Rectangle allocatedSpace) {
    if (pointsToTry.isEmpty()) {
      pointsToTry.add(
          Point.builder()
              .x(allocatedSpace.getX())
              .y(allocatedSpace.getY())
              .build());
    }
  }


  @Override
  public Type getType() {
    return GREEDY;
  }

}
