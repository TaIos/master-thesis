package models.entity;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EvaluatedIndividual {

  private Individual individual;
  private List<Orientation> orientationsCapped;
  private EvaluatedSlicingLayout layout;
}
