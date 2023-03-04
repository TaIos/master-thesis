package models.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class EvaluatedSlicingLayout extends PlacedSlicingLayout {

  private Double objectiveValue;


}
