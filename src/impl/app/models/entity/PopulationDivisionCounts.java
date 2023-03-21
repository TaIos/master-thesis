package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PopulationDivisionCounts {

  private Integer elite;
  private Integer average;
  private Integer worst;

  private Integer children;
  private Integer mutant;
  private Integer winner;
  private Integer random;
}
