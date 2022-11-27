package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Facility implements Cloneable {

  private String ident;
  private Double area;
  private Rectangle placement;

  @Override
  public Facility clone() {
    try {
      Facility clone = (Facility) super.clone();
      clone.ident = ident;
      clone.area = area;
      if (placement != null) clone.placement = placement.clone();
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
