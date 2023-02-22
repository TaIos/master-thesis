package models.entity;

import static logic.objective.Objective.OBJECTIVE_VALUE_MAX;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Individual implements Comparable<Individual> {

  private List<Painting> paintingSeq;
  private List<Double> paintingSeqRandomKey;

  private List<Double> slicingOrderRandomKey;

  private List<OrientationProbability> orientationProb;
  private List<Orientation> orientations;
  private List<Orientation> orientationsResolved;

  @Builder.Default
  private Double objectiveValue = OBJECTIVE_VALUE_MAX;

  @Override
  public int compareTo(Individual individual) {
    return objectiveValue.compareTo(individual.objectiveValue);
  }

}
