package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Painting implements Cloneable {

  private String ident;
  private Rectangle placement;

  private Integer width;
  private Integer height;
  private Integer area;

  @Override
  public Painting clone() {
    try {
      Painting clone = (Painting) super.clone();
      // TODO: copy mutable state here, so the clone can't change the internals of the original
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
