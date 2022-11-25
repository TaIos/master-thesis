package logic.genetic;

import lombok.Builder;
import lombok.Getter;
import models.entity.Individual;
import models.entity.Population;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HallOfFame {

  private final List<HallOfFameRecord> records;

  public HallOfFame(int initialCapacity) {
    records = new ArrayList<>(initialCapacity);
  }

  public void log(Population pop, int iteration) {
    records.add(
        HallOfFameRecord.builder()
            .iteration(iteration)
            .objectiveMin(pop.getObjectiveMin())
            .objectiveMax(pop.getObjectiveMax())
            .objectiveAvg(pop.getObjectiveAvg())
            .bestIndividual(pop.getBestIndividual().clone())
            .build());
  }

  @Getter
  @Builder
  public static class HallOfFameRecord {
    private int iteration;
    private double objectiveMin;
    private double objectiveMax;
    private double objectiveAvg;
    private Individual bestIndividual;
  }
}
