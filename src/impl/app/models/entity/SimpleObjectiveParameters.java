package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleObjectiveParameters {

  private Double overlappingPenalizationConstant;
  private Double outsideOfAllocatedAreaPenalizationConstant;
}
