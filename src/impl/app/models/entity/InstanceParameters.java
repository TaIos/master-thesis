package models.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InstanceParameters {

  private Layout layout;
  private Integer paintingCount;
  private List<Painting> paintings;
}
