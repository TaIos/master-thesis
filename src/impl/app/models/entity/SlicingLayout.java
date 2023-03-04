package models.entity;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SlicingLayout {

  protected List<Painting> paintings;
  protected List<Rectangle> allocatedSpace;

}
