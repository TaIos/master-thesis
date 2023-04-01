package logic.metric;

import static logic.metric.Metric.ObjectiveType.EUCLIDEAN;

public class EuclideanMetric implements Metric {

  @Override
  public double calculate(double x1, double y1, double x2, double y2) {
    double xDiff = x2 - x1;
    double yDiff = y2 - y1;
    return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
  }

  @Override
  public ObjectiveType getType() {
    return EUCLIDEAN;
  }
}
