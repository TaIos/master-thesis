package models.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaintingPlacement {

  private Painting painting;
  private Rectangle allocatedSpace;
  private Rectangle placement;

}
