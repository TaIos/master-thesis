package models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.oval.constraint.AssertValid;
import net.sf.oval.constraint.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateComputationDto implements Dto {

  @NotNull
  @AssertValid
  private InstanceParametersDto instanceParameters;

  @NotNull
  @AssertValid
  private GAParametersDto gaParameters;

  @NotNull
  @AssertValid
  private ObjectiveParametersDto objectiveParameters;
}
