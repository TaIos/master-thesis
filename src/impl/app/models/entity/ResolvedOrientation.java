package models.entity;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.EnumTypeInterface;
import utils.JavaUtils;

@Getter
@AllArgsConstructor
public class ResolvedOrientation {

  private ResolvedOrientationType type;

  public enum ResolvedOrientationType implements EnumTypeInterface {
    HORIZONTAL("H"),
    VERTICAL("V");

    public final String label;

    ResolvedOrientationType(String label) {
      this.label = label;
    }

    public static Optional<ResolvedOrientationType> getForLabel(String label) {
      return JavaUtils.getForLabel(values(), label);
    }

    @Override
    public String getLabel() {
      return label;
    }
  }
}
