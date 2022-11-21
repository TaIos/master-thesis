package models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionalComputationResultDto implements Dto {
  private String exception;
  private GAParametersDto gaParameters;
  private InstanceParametersDto instanceParameters;
}
