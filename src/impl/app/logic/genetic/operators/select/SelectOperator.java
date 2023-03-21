package logic.genetic.operators.select;

import java.util.List;
import java.util.Optional;
import logic.genetic.evaluator.Evaluator;
import models.entity.Individual;
import utils.EnumTypeInterface;
import utils.JavaUtils;

public interface SelectOperator {

  List<Individual> select(List<Individual> population, int size, Evaluator evaluator);

  Type getType();

  enum Type implements EnumTypeInterface {
    BEST("best"),
    TOURNAMENT("tournament");

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
