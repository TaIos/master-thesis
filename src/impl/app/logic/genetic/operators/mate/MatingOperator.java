package logic.genetic.operators.mate;

import java.util.Optional;
import models.entity.Individual;
import utils.EnumTypeInterface;
import utils.JavaUtils;

public interface MatingOperator {

  Individual mate(Individual p1, Individual p2);

  Type getType();

  enum Type implements EnumTypeInterface {
    REPEAT_FIRST_PARENT("repeatFirstParent"),
    NORMALIZED_PROBABILITY_VECTOR_SUM_CROSSOVER("normalizedProbabilityVectorSum");

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
