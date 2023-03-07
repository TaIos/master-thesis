package models.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class PlacedSlicingLayout {

  private List<PaintingPlacement> placements;

}
