package models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RectangleDto implements Dto {

  @NotNull
  private Integer x;
  @NotNull
  private Integer y;
  @NotNull
  @NotNegative
  private Integer width;
  @NotNull
  @NotNegative
  private Integer height;
}
