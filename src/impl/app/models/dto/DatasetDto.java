package models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.oval.constraint.AssertURL;
import net.sf.oval.constraint.AssertValid;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DatasetDto implements Dto {

  @NotNull @NotEmpty String name;

  @NotNull @AssertURL String source;

  @NotNull
  @AssertValid
  private InstanceParametersDto data;
}
