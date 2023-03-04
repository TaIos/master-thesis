package models.entity;

import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PlacedSlicingLayout {

  private List<PaintingPlacement> placements;

}
