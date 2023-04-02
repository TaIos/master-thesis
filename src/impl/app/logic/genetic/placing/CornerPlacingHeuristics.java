package logic.genetic.placing;

import static logic.genetic.placing.PlacingHeuristics.Type.CORNER;

import java.util.ArrayList;
import java.util.List;
import models.entity.Painting;
import models.entity.Point;
import models.entity.Rectangle;

public class CornerPlacingHeuristics extends BasePlacingHeuristics {


  @Override
  public List<Point> createPointsToTry(Painting painting, Rectangle allocatedSpace) {
    List<Point> pointsToTry = new ArrayList<>();
    addBottomLeftAlways(allocatedSpace, pointsToTry);
    addBottomRight(painting, allocatedSpace, pointsToTry);
    addTopLeft(painting, allocatedSpace, pointsToTry);
    addTopRight(painting, allocatedSpace, pointsToTry);
    return pointsToTry;
  }

  private void addBottomLeftAlways(Rectangle allocatedSpace, List<Point> pointsToTry) {
    pointsToTry.add(
        Point.builder()
            .x(allocatedSpace.getX())
            .y(allocatedSpace.getY())
            .build()
    );
  }

  private void addBottomRight(Painting painting, Rectangle allocatedSpace,
      List<Point> pointsToTry) {
    int xAdd = allocatedSpace.getWidth() - painting.getWidth();
    if (xAdd > 0) {
      pointsToTry.add(
          Point.builder()
              .x(allocatedSpace.getX() + xAdd)
              .y(allocatedSpace.getY())
              .build()
      );
    }
  }

  private void addTopLeft(Painting painting, Rectangle allocatedSpace, List<Point> pointsToTry) {
    int yAdd = allocatedSpace.getHeight() - painting.getHeight();
    if (yAdd > 0) {
      pointsToTry.add(
          Point.builder()
              .x(allocatedSpace.getX())
              .y(allocatedSpace.getY() + yAdd)
              .build()
      );
    }
  }

  private void addTopRight(Painting painting, Rectangle allocatedSpace, List<Point> pointsToTry) {
    int xAdd = allocatedSpace.getWidth() - painting.getWidth();
    int yAdd = allocatedSpace.getHeight() - painting.getHeight();
    if (xAdd > 0 && yAdd > 0) {
      pointsToTry.add(
          Point.builder()
              .x(allocatedSpace.getX() + xAdd)
              .y(allocatedSpace.getY() + yAdd)
              .build()
      );
    }
  }

  @Override
  public Type getType() {
    return CORNER;
  }
}
