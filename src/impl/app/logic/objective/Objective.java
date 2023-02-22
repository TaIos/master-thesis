package logic.objective;

import java.util.List;
import java.util.Optional;
import models.entity.Painting;
import utils.EnumTypeInterface;
import utils.JavaUtils;

public interface Objective {

  double OBJECTIVE_VALUE_MAX = Double.MAX_VALUE;

  double eval(List<Painting> paintingList);


  Type getType();


  enum Type implements EnumTypeInterface {
    SIMPLE("simple");

    final String label;

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
