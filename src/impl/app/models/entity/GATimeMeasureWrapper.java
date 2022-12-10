package models.entity;

import java.util.concurrent.Callable;
import logic.genetic.algorithm.GeneticAlgorithm;

public class GATimeMeasureWrapper implements Callable<ComputationResult> {

  private final GeneticAlgorithm geneticAlgorithm;

  public GATimeMeasureWrapper(GeneticAlgorithm geneticAlgorithm) {
    this.geneticAlgorithm = geneticAlgorithm;
  }

  @Override
  public ComputationResult call() {
    Long start = System.currentTimeMillis();
    GAResult result = geneticAlgorithm.call();
    Long end = System.currentTimeMillis();
    return new ComputationResult(result, end - start);
  }
}
