package models.entity;

import static models.entity.Orientation.OrientationType;
import static models.entity.Orientation.OrientationType.HORIZONTAL;
import static models.entity.Orientation.OrientationType.VERTICAL;
import static models.entity.Orientation.OrientationType.WILD_CARD;
import static utils.JavaUtils.getMax3Index;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrientationProbability {

  private List<Double> probabilityVector;

  public OrientationType getMostProbable() {
    switch (getMax3Index(probabilityVector)) {
      case 0:
        return HORIZONTAL;
      case 1:
        return VERTICAL;
      case 2:
        return WILD_CARD;
      default:
        throw new IllegalArgumentException("Unexpected index. It should be one of 0,1 or 3");

    }
  }
}
