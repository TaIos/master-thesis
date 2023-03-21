package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InitialPopulationDivisionCounts {

  private Integer random;
  private Integer greedy;
}
