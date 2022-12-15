package models.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ComputationResultDto implements Dto {

  private String applicationVersion;
  private ZonedDateTime createdAt;
  private Long durationMillis;
  private GAResultDto gaResult;
  private GAParametersDto gaParameters;
  private InstanceParametersDto instanceParameters;
  private ObjectiveParametersDto objectiveParameters;
}
