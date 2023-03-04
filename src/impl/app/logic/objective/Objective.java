package logic.objective;

import java.util.Optional;
import models.entity.EvaluatedSlicingLayout;
import models.entity.PlacedSlicingLayout;
import utils.EnumTypeInterface;
import utils.JavaUtils;

public interface Objective {


  EvaluatedSlicingLayout eval(PlacedSlicingLayout placedSlicingLayout);


  ObjectiveType getType();


  enum ObjectiveType implements EnumTypeInterface {
    SIMPLE("simple");

    final String label;

    ObjectiveType(String label) {
      this.label = label;
    }

    public static Optional<ObjectiveType> getForLabel(String label) {
      return JavaUtils.getForLabel(values(), label);
    }

    @Override
    public String getLabel() {
      return label;
    }
  }
}
