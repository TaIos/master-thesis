package models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.oval.constraint.AssertValid;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstanceParametersDto implements Dto {

  @NotNull
  @Min(2)
  private Integer facilityCount;

  @NotNull
  @Min(0)
  private Integer emptySpace;

  @NotNull @AssertValid private LayoutDto layout;

  @NotNull
  @MinSize(2)
  @AssertValid
  List<FacilityDimensionDto> dimension;

  @NotNull @AssertValid List<FacilityFlowDto> flow;
}
