package logic.genetic.operators.mutate;

import java.util.Optional;
import models.entity.Individual;
import utils.EnumTypeInterface;
import utils.JavaUtils;

public interface MutateOperator {

  void mutate(Individual ind);

  Type getType();

  enum Type implements EnumTypeInterface {
    FLIP_SLICING_ORDER("flipSlicingOrder"),
    FLIP_PAINTING_SEQUENCE("flipPaintingSequence"),
    FLIP_ORIENTATION("flipOrientation"),
    FLIP_ONE_PART_AT_RANDOM("flipOnePartAtRandom");

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
