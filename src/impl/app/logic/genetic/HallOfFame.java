package logic.genetic;

import factory.copy_factory.IndividualCopyFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import models.entity.Individual;
import models.entity.Population;

@Getter
public class HallOfFame {

  private final List<HallOfFameRecord> records;
  private final IndividualCopyFactory individualCopyFactory;

  public HallOfFame(int initialCapacity, IndividualCopyFactory individualCopyFactory) {
    records = new ArrayList<>(initialCapacity);
    this.individualCopyFactory = individualCopyFactory;
  }

  public void log(Population pop, int iteration) {
    records.add(
        HallOfFameRecord.builder()
            .iteration(iteration)
            .objectiveMin(pop.getObjectiveMin())
            .objectiveMax(pop.getObjectiveMax())
            .objectiveAvg(pop.getObjectiveAvg())
            .bestIndividual(individualCopyFactory.createCopy(pop.getBestIndividual()))
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
