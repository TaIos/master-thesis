package models.entity;

import static models.entity.Orientation.Type.HORIZONTAL;
import static models.entity.Orientation.Type.VERTICAL;

import java.util.Optional;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import utils.EnumTypeInterface;
import utils.JavaUtils;

@Data
@Builder
@AllArgsConstructor
public class Orientation {

  private Type type;

  public void flip() {
    if (type.equals(HORIZONTAL)) {
      type = VERTICAL;
    }
    type = HORIZONTAL;
  }

  public enum Type implements EnumTypeInterface {
    HORIZONTAL("H"),
    VERTICAL("V");

    public final String label;

    Type(String label) {
      this.label = label;
    }

    public static Optional<Type> getForLabel(String label) {
      return JavaUtils.getForLabel(values(), label);
    }

    public static Type getRandom(Random rnd) {
      return Type.values()[rnd.nextInt(Type.values().length)];
    }

    @Override
    public String getLabel() {
      return label;
    }
  }
}
