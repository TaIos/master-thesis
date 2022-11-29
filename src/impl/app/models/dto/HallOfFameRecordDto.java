package models.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import utils.serialization.DoubleSerializer;

@Data
@Builder
@AllArgsConstructor
public class HallOfFameRecordDto implements Dto {
  private Integer iteration;

  @JsonSerialize(using = DoubleSerializer.class)
  private Double objectiveMin;

  @JsonSerialize(using = DoubleSerializer.class)
  private Double objectiveMax;

  @JsonSerialize(using = DoubleSerializer.class)
  private Double objectiveAvg;

  private IndividualDto bestIndividual;
}
