package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Point implements Cloneable {

  private Double x;
  private Double y;

  @Override
  public Point clone() {
    try {
      Point clone = (Point) super.clone();
      clone.x = x;
      clone.y = y;
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
