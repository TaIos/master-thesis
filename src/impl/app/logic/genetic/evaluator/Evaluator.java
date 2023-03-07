package logic.genetic.evaluator;

import java.util.Optional;
import logic.objective.ObjectiveValueComparator;
import models.entity.EvaluatedSlicingLayout;
import models.entity.Individual;
import utils.EnumTypeInterface;
import utils.JavaUtils;

public interface Evaluator {


  EvaluatedSlicingLayout eval(Individual ind);

  ObjectiveValueComparator getObjectiveValueComparator();

  Type getType();

  enum Type implements EnumTypeInterface {
    GENETIC("ga");

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
