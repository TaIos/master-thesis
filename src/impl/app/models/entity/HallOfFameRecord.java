package models.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HallOfFameRecord {

  private int iteration;
  private double objectiveMin;
  private double objectiveMax;
  private double objectiveAvg;
  private EvaluatedIndividual bestIndividual;
}