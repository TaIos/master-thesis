package models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ComputationResultDto implements Dto {

  private String applicationVersion;
  private Long durationMillis;
  private GAResultDto gaResult;
  private GAParametersDto gaParameters;
  private InstanceParametersDto instanceParameters;
}
