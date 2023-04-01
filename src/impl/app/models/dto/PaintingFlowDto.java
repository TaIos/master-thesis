package models.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import utils.serialization.DoubleSerializer;

@Data
@Builder
@AllArgsConstructor
public class PaintingFlowDto {

  @NotNull
  @NotEmpty
  private String from;

  @NotNull
  @NotEmpty
  private String to;

  @NotNull
  @Min(0)
  @JsonSerialize(using = DoubleSerializer.class)
  private Double flow;
}
