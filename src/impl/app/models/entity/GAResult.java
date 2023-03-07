package models.entity;

import logic.genetic.HallOfFame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GAResult {

  public GAResult(HallOfFame hof) {
    hallOfFame = hof;
    layout = hof.getBestRecord().getBestIndividual().getLayout();
  }

  private HallOfFame hallOfFame;
  private EvaluatedSlicingLayout layout;
}
