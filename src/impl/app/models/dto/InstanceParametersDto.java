package models.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.oval.constraint.AssertValid;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstanceParametersDto implements Dto {

  @NotNull @AssertValid @MinSize(1) List<PaintingDto> paintings;

  @NotNull
  @AssertValid
  private LayoutDto layout;
}
