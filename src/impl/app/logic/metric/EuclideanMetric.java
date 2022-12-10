package logic.metric;

import models.entity.Painting;
import models.entity.Point;

public class EuclideanMetric implements Metric {

  @Override
  public Double eval(Painting p1, Painting p2) {
    Point c1 = p1.getPlacement().getCenter();
    Point c2 = p2.getPlacement().getCenter();
    return Math.sqrt(Math.pow(c1.getX() - c2.getX(), 2) + Math.pow(c1.getY() - c2.getY(), 2));
  }

  @Override
  public Type getType() {
    return Type.EUCLIDEAN;
  }
}
