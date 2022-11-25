package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Rectangle {

  private Integer x;
  private Integer y;
  private Integer width;
  private Integer height;
  private Point center;

  // lazy evaluation
  public Point getCenter() {
    if (center == null) center = new Point((x + width) / 2d, (y + height) / 2d);
    return center;
  }

  @Override
  public String toString() {
    return String.format("%s,%s,%s,%s", x, y, width, height);
  }
}
