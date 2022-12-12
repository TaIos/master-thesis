package models.entity;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Painting implements Cloneable {

  private String ident;
  private Rectangle allocatedSpace;
  private Point placement;

  private Integer width;
  private Integer height;
  private Integer area;

  @Override
  public Painting clone() {
    try {
      Painting clone = (Painting) super.clone();
      clone.ident = ident;
      clone.allocatedSpace = Optional.ofNullable(allocatedSpace).map(Rectangle::clone).orElse(null);
      clone.placement = Optional.ofNullable(placement).map(Point::clone).orElse(null);
      clone.width = width;
      clone.height = height;
      clone.area = area;
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
