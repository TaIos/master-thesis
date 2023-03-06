package logic.objective.parts;

import java.util.List;
import models.entity.Rectangle;

public class OverlappingRectanglesCount {

  public int calculate(List<Rectangle> recs) {
    int sum = 0;
    for (int i = 0; i < recs.size(); i++) {
      for (int j = i + 1; j < recs.size(); j++) {
        if (doRectanglesOverlap(recs.get(i), recs.get(j))) {
          sum += 1;
        }
      }
    }
    return sum;
  }

  private boolean doRectanglesOverlap(Rectangle r1, Rectangle r2) {
    return r1.getX() < r2.getX() + r2.getWidth()
        && r2.getX() < r1.getX() + r1.getWidth()
        && r1.getY() < r2.getY() + r2.getHeight()
        && r2.getY() < r1.getY() + r1.getHeight();
  }

}
