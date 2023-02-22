package models.entity;

import static logic.objective.Objective.OBJECTIVE_VALUE_MAX;

import java.util.Collections;
import java.util.List;
import logic.genetic.Evaluator;
import lombok.Getter;

@Getter
public class Population {

  private final List<Individual> individualList;

  public Population(List<Individual> individualList, Evaluator evaluator) {
    individualList.parallelStream().forEach(evaluator::eval);
    Collections.sort(individualList);
    this.individualList = Collections.unmodifiableList(individualList);
  }

  public Individual getBestIndividual() {
    return individualList.get(0);
  }

  public double getObjectiveMin() {
    return individualList.get(0).getObjectiveValue();
  }

  public double getObjectiveMax() {
    return individualList.get(individualList.size() - 1).getObjectiveValue();
  }

  public double getObjectiveAvg() {
    return individualList.stream()
        .mapToDouble(Individual::getObjectiveValue)
        .average()
        .orElse(OBJECTIVE_VALUE_MAX);
  }

  public int size() {
    return individualList.size();
  }
}
