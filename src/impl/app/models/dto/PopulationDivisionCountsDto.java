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
public class PopulationDivisionCountsDto {

  @Min(0)
  @Max(1)
  @NotNull
  private Double elite;

  @Min(0)
  @Max(1)
  @NotNull
  private Double average;

  @Min(0)
  @Max(1)
  @NotNull
  private Double worst;

  @Min(0)
  @Max(1)
  @NotNull
  private Double children;

  @Min(0)
  @Max(1)
  @NotNull
  private Double mutant;

  @Min(0)
  @Max(1)
  @NotNull
  private Double winner;

  @Min(0)
  @Max(1)
  @NotNull
  private Double random;
}
