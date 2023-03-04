package logic.objective.parts;

import models.entity.Rectangle;

public class IsOutsideOfAllocatedArea {

  public boolean calculate(Rectangle rec, Rectangle allocatedArea) {
    return calculate(
        rec.getX(), rec.getY(),
        rec.getWidth(), rec.getHeight(),
        allocatedArea.getX(), allocatedArea.getY(),
        allocatedArea.getWidth(), allocatedArea.getHeight()
    );
  }

  private boolean calculate(
      int x1, int y1, int w1, int h1,
      int x2, int y2, int w2, int h2) {
    return x1 < x2
        || y1 < y2
        || x1 + w1 > x2 + w2
        || y1 + h1 > y2 + h2;
  }

}
