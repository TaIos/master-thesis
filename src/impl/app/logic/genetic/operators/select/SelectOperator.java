package logic.genetic.operators.select;

import java.util.List;
import java.util.Optional;
import models.entity.Individual;
import models.entity.Population;
import utils.EnumTypeInterface;
import utils.JavaUtils;

public interface SelectOperator {

  List<Individual> select(Population population, int size);

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
