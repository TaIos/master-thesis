package models.entity;

import static utils.JavaUtils.getMax3Index;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import models.entity.Orientation.Type;

@Data
@Builder
@AllArgsConstructor
public class OrientationProbability {

  private List<Double> probabilityVector;

  public Orientation.Type getMostProbable() {
    switch (getMax3Index(probabilityVector)) {
      case 0:
        return Type.HORIZONTAL;
      case 1:
        return Type.VERTICAL;
      case 2:
        return Type.WILD_CARD;
      default:
        throw new IllegalArgumentException("Unexpected index. It should be one of 0,1 or 3");

    }
  }


}
