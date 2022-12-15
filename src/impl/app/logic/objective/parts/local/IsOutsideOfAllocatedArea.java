package logic.objective.parts.local;

import logic.objective.parts.local.LocalObjectivePart;
import models.entity.Painting;

public class IsOutsideOfAllocatedArea implements LocalObjectivePart<Boolean> {

  @Override
  public Boolean eval(Painting p) {
    return (p.getPlacement().getX() < p.getAllocatedSpace().getX())
        || (p.getPlacement().getY() < p.getAllocatedSpace().getY())
        || (p.getPlacement().getX() + p.getWidth()
        > p.getAllocatedSpace().getX() + p.getAllocatedSpace().getWidth())
        || (p.getPlacement().getY() + p.getHeight()
        > p.getAllocatedSpace().getY() + p.getAllocatedSpace().getHeight());
  }

}
