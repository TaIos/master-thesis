package logic.genetic.operators.mutate;

import java.util.Optional;
import models.entity.Individual;
import utils.EnumTypeInterface;
import utils.JavaUtils;

public interface MutateOperator {

  void mutate(Individual ind);

  Type getType();

  enum Type implements EnumTypeInterface {
    FLIP_ONES_SLICING_ORDER_AT_RANDOM("flipOneSlicingOrderAtRandom");

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
