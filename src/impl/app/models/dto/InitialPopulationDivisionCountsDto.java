package models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

@Data
@Builder
@AllArgsConstructor
public class InitialPopulationDivisionCountsDto {

  @Min(0)
  @Max(1)
  @NotNull
  private Double random;

  @Min(0)
  @Max(1)
  @NotNull
  private Double greedy;
}
