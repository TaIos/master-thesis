package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class InstanceParameters {
  private Integer facilityCount;
  private Integer emptySpace;
  private Rectangle layout;
  private List<Facility> facilities;
  private Flow flow;
}
