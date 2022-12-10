package models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.oval.constraint.AssertValid;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateComputationFromDatasetDto implements Dto {

  @NotNull @NotEmpty String datasetName;
  @NotNull
  @AssertValid
  private GAParametersDto gaParams;
}
