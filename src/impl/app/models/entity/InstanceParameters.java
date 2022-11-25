package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InstanceParameters {
  private Rectangle grid;
  private Integer facilityCount;
}
