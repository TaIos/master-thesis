package models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

@Data
@Builder
@AllArgsConstructor
public class FacilityFlowDto {
  @NotNull @NotEmpty private String from;
  @NotNull @NotEmpty private String to;

  @NotNull
  @Min(0)
  private Double flow;
}
