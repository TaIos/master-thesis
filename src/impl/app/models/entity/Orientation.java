package models.entity;

import java.util.List;
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

  public enum Type implements EnumTypeInterface {
    HORIZONTAL("H"),
    VERTICAL("V"),
    WILD_CARD("*");

    public final String label;

    public static final List<Type> WITHOUT_WILD_CARD = List.of(HORIZONTAL, VERTICAL);

    Type(String label) {
      this.label = label;
    }

    public static Optional<Type> getForLabel(String label) {
      return JavaUtils.getForLabel(values(), label);
    }

    public static Type getRandom(Random rnd) {
      return Type.values()[rnd.nextInt(Type.values().length)];
    }

    public static Type getRandomWithoutWildCard(Random rnd) {
      return WITHOUT_WILD_CARD.get(rnd.nextInt(WITHOUT_WILD_CARD.size()));
    }

    @Override
    public String getLabel() {
      return label;
    }
  }
}
