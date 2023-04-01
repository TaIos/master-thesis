package models.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Painting {

  private final String ident;
  private final Integer width;
  private final Integer height;
  private final Integer area;

}
