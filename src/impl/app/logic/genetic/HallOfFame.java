package logic.genetic;

import static utils.DelayedFormatter.format;

import factory.copy_factory.IndividualCopyFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import logic.objective.ObjectiveValueComparator;
import lombok.Getter;
import models.entity.EvaluatedIndividual;
import models.entity.GAParameters;
import models.entity.HallOfFameRecord;
import models.entity.Population;
import org.slf4j.Logger;

@Getter
public class HallOfFame {

  private final List<HallOfFameRecord> records;
  private final IndividualCopyFactory individualCopyFactory;
  private final ObjectiveValueComparator objectiveValueComparator;

  public HallOfFame(int initialCapacity, IndividualCopyFactory individualCopyFactory,
      ObjectiveValueComparator objectiveValueComparator) {
    records = new ArrayList<>(initialCapacity);
    this.individualCopyFactory = individualCopyFactory;
    this.objectiveValueComparator = objectiveValueComparator;
  }

  public HallOfFame log(Population pop, int iteration) {
    records.add(
        HallOfFameRecord.builder()
            .iteration(iteration)
            .objectiveMin(pop.getObjectiveMin())
            .objectiveMax(pop.getObjectiveMax())
            .objectiveAvg(pop.getObjectiveAvg())
            .bestIndividual(
                EvaluatedIndividual.builder()
                    .individual(
                        individualCopyFactory.createCopy(pop.getBestIndividual().getIndividual()))
                    .layout(pop.getBestIndividual().getLayout())
                    .build()
            )
            .build());
    return this;
  }

  public HallOfFameRecord getBestRecord() {
    return records.stream()
        .min((o1, o2) -> objectiveValueComparator.compare(
            o1.getBestIndividual().getLayout().getObjectiveValue(),
            o2.getBestIndividual().getLayout().getObjectiveValue()))
        .orElseThrow(() -> new NoSuchElementException("Records are empty"));

  }

  public void withPrintLast(Logger logger, GAParameters gaParams) {
    HallOfFameRecord last = records.get(records.size() - 1);
    logger.info("iteration={}/{}, min={}, max={}, avg={}",
        last.getIteration(),
        gaParams.getMaxNumberOfIter(),
        format("%.02f", last.getObjectiveMin()),
        format("%.02f", last.getObjectiveMax()),
        format("%.02f", last.getObjectiveAvg()));
  }


}
