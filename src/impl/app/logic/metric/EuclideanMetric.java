package logic.metric;

import models.entity.Facility;
import models.entity.Point;

public class EuclideanMetric implements Metric {
  @Override
  public Double eval(Facility f1, Facility f2) {
    Point c1 = f1.getPlacement().getCenter();
    Point c2 = f2.getPlacement().getCenter();
    return Math.sqrt(Math.pow(c1.getX() - c2.getX(), 2) + Math.pow(c1.getY() - c2.getY(), 2));
  }

  @Override
  public Type getType() {
    return Type.EUCLIDEAN;
  }
}
