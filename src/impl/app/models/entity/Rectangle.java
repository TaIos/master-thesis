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
  private Point center;

  // lazy evaluation
  public Point getCenter() {
    if (center == null) {
      center = new Point((x + width) / 2d, (y + height) / 2d);
    }
    return center;
  }

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
      if (center != null) {
        clone.center = center.clone();
      }
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
