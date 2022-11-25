package models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class HallOfFameRecordDto implements Dto {
  private Integer iteration;
  private Double objectiveMin;
  private Double objectiveMax;
  private Double objectiveAvg;
  private IndividualDto bestIndividual;
}
