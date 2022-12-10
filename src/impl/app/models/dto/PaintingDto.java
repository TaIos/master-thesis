package models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaintingDto {

  @NotNull
  @NotBlank
  private String ident;

  @NotNull
  @Min(0)
  private Integer width;

  @NotNull
  @Min(0)
  private Integer height;
}
