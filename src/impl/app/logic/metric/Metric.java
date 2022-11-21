package logic.metric;

import models.entity.Facility;
import utils.EnumTypeInterface;
import utils.JavaUtils;

import java.util.Optional;

public interface Metric {
  Double eval(Facility f1, Facility f2);

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
