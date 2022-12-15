package models.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObjectiveParametersDto implements Dto {

  @NotNull
  @NotBlank
  private String name;

  @NotBlank
  private JsonNode params;
}
