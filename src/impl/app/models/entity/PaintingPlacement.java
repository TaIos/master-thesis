package models.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaintingPlacement {

  private Rectangle allocatedSpace;
  private Rectangle placement;

}
