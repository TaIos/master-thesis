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
public class InstanceParametersDto implements Dto {
  @NotNull @AssertValid private RectangleDto grid;
}
