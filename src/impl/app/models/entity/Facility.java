package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Facility implements Cloneable {

  private String ident;
  private Rectangle placement;
}
