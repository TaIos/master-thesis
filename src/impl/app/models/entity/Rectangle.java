package models.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Rectangle {

  private Integer x;
  private Integer y;
  private Integer width;
  private Integer height;

  @Override
  public String toString() {
    return String.format("%s,%s,%s,%s", x, y, width, height);
  }
}
