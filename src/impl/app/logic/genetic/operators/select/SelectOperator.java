package logic.genetic.operators.select;

import java.util.List;
import java.util.Optional;
import models.entity.Individual;
import utils.EnumTypeInterface;
import utils.JavaUtils;

public interface SelectOperator {

  List<Individual> select(List<Individual> individuals, Integer k);

  Type getType();

  enum Type implements EnumTypeInterface {
    BEST("best");

    public final String label;

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
