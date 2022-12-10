package logic.metric;

import java.util.Optional;
import models.entity.Painting;
import utils.EnumTypeInterface;
import utils.JavaUtils;

public interface Metric {

  Double eval(Painting p1, Painting p2);

  Type getType();

  enum Type implements EnumTypeInterface {
    DUMMY("dummy"),
    EUCLIDEAN("euclidean");

    private final String label;

    Type(String label) {
      this.label = label;
    }

    public static Optional<Type> getForLabel(String label) {
      return JavaUtils.getForLabel(values(), label);
    }

    @Override
    public String getLabel() {
      return label;
    }
  }
}
