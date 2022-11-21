package logic.genetic.operators.mutate;

import models.entity.Individual;
import utils.EnumTypeInterface;
import utils.JavaUtils;

import java.util.Optional;

public interface MutateOperator {
  void mutate(Individual ind);

  Type getType();

  enum Type implements EnumTypeInterface {
    FLIP_ONE_ORIENTATION_AT_RANDOM("flipOneOrientationAtRandom");

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
