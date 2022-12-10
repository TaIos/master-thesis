package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.mariuszgromada.math.mxparser.Function;

@Data
@Builder
@AllArgsConstructor
public class Layout {

  private Rectangle boundingRectangle;
  private Function evalFunc;
}
