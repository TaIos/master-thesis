package models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComputationResultDto implements Dto {

  private Long durationMillis;
  private GAResultDto gaResult;
  private GAParametersDto gaParameters;
  private InstanceParametersDto instanceParameters;
}
