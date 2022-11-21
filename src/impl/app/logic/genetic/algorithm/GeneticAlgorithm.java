package logic.genetic.algorithm;

import models.entity.GAResult;
import utils.EnumTypeInterface;
import utils.JavaUtils;

import java.util.Optional;
import java.util.concurrent.Callable;

public interface GeneticAlgorithm extends Callable<GAResult> {

  @Override
  GAResult call();

  Type getType();

  enum Type implements EnumTypeInterface {
    SIMPLE_GA("simpleGa");

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
