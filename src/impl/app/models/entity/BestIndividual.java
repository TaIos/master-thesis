package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BestIndividual {

  private Individual bestIndividual;
  private int iteration;

  public BestIndividual(Population population, int iteration) {
    update(population, iteration);
  }

  public void update(Individual other, int iteration) {
    if (bestIndividual == null || other.getObjectiveValue() < bestIndividual.getObjectiveValue()) {
      bestIndividual = other.clone();
      this.iteration = iteration;
    }
  }

  public void update(Population population, int iteration) {
    update(population.getBestIndividual(), iteration);
  }
}
