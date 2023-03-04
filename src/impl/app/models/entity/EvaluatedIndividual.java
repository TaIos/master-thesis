package models.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EvaluatedIndividual {

  private Individual individual;
  private EvaluatedSlicingLayout layout;
}
