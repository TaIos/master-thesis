package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Painting {

  private String ident;
  private Rectangle allocatedSpace;
  private Point placement;

  private Integer width;
  private Integer height;
  private Integer area;

}
