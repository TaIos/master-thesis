package logic.genetic.placing;

import java.util.Optional;
import logic.objective.Objective;
import models.entity.PaintingsFlow;
import models.entity.PlacedSlicingLayout;
import models.entity.SlicingLayout;
import utils.EnumTypeInterface;
import utils.JavaUtils;

public interface PlacingHeuristics {

  PlacedSlicingLayout place(SlicingLayout slicingLayout, Objective objective, PaintingsFlow flow);


  Type getType();

  enum Type implements EnumTypeInterface {
    BOTTOM_LEFT("bottomLeft"),
    GREEDY("greedy"),
    CORNER("corner");

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
