package logic.metric;

import java.util.Optional;
import models.entity.Point;
import utils.EnumTypeInterface;
import utils.JavaUtils;

public interface Metric {

  double calculate(double x1, double y1, double x2, double y2);


  default double calculate(Point p1, Point p2) {
    return calculate(p1.getX(), p1.getY(), p2.getX(), p2.getY());
  }

  ObjectiveType getType();

  enum ObjectiveType implements EnumTypeInterface {
    EUCLIDEAN("euclidean");

    final String label;

    ObjectiveType(String label) {
      this.label = label;
    }

    public static Optional<ObjectiveType> getForLabel(String label) {
      return JavaUtils.getForLabel(values(), label);
    }

    @Override
    public String getLabel() {
      return label;
    }


  }
}
