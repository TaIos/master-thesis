package models.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Point {

  private Integer x;
  private Integer y;
}
