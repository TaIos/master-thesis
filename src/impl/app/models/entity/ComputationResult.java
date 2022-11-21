package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ComputationResult {
  private GAResult gaResult;
  private Long durationMillis;
}
