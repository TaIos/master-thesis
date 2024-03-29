package models.entity;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.EnumTypeInterface;
import utils.JavaUtils;

@Getter
@AllArgsConstructor
public class Orientation {

  private OrientationType type;

  public enum OrientationType implements EnumTypeInterface {
    HORIZONTAL("H"),
    VERTICAL("V"),
    WILD_CARD("*");

    public final String label;

    OrientationType(String label) {
      this.label = label;
    }

    public static Optional<OrientationType> getForLabel(String label) {
      return JavaUtils.getForLabel(values(), label);
    }

    @Override
    public String getLabel() {
      return label;
    }
  }
}
