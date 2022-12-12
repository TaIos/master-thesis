package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Rectangle implements Cloneable {

  private Integer x;
  private Integer y;
  private Integer width;
  private Integer height;

  @Override
  public String toString() {
    return String.format("%s,%s,%s,%s", x, y, width, height);
  }

  @Override
  public Rectangle clone() {
    try {
      Rectangle clone = (Rectangle) super.clone();
      clone.x = x;
      clone.y = y;
      clone.width = width;
      clone.height = height;
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
