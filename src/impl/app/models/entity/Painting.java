package models.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Painting {

  protected String ident;
  protected Integer width;
  protected Integer height;

  public double getArea() {
    return width * height;
  }
}
