package models.entity;


import static logic.objective.ObjectiveValueComparator.OBJECTIVE_VALUE_MAX;

import java.util.List;
import java.util.stream.Collectors;
import logic.genetic.Evaluator;
import lombok.Getter;

@Getter
public class Population {

  private final List<EvaluatedIndividual> evaluatedIndividuals;

  public Population(List<Individual> individualList, Evaluator evaluator) {
    evaluatedIndividuals = individualList.parallelStream()
        .map(ind -> EvaluatedIndividual.builder()
            .individual(ind)
            .layout(evaluator.eval(ind))
            .build()
        ).sorted((o1, o2) -> evaluator.getObjectiveValueComparator()
            .compare(o1.getLayout(), o2.getLayout()))
        .collect(Collectors.toUnmodifiableList());
  }

  public EvaluatedIndividual getBestIndividual() {
    return evaluatedIndividuals.get(0);
  }

  public double getObjectiveMin() {
    return evaluatedIndividuals.get(0).getLayout().getObjectiveValue();
  }

  public double getObjectiveMax() {
    return evaluatedIndividuals.get(evaluatedIndividuals.size() - 1).getLayout()
        .getObjectiveValue();
  }

  public double getObjectiveAvg() {
    return evaluatedIndividuals.stream()
        .map(EvaluatedIndividual::getLayout)
        .mapToDouble(EvaluatedSlicingLayout::getObjectiveValue)
        .average()
        .orElse(OBJECTIVE_VALUE_MAX);
  }

  public int size() {
    return evaluatedIndividuals.size();
  }
}
