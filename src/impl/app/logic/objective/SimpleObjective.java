package logic.objective;

import static logic.objective.Objective.Type.SIMPLE;

import models.entity.Painting;
import models.entity.Point;

public class SimpleObjective implements Objective {


  @Override
  public double eval(Painting painting) {
    // TODO
    painting.setPlacement(
        new Point(painting.getAllocatedSpace().getX(), painting.getAllocatedSpace().getY()));
    return 0;
  }

  @Override
  public Type getType() {
    return SIMPLE;
  }
}
