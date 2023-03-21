package models.entity;


import static logic.objective.ObjectiveValueComparator.OBJECTIVE_VALUE_MAX;

import java.util.List;
import java.util.stream.Collectors;
import logic.genetic.evaluator.Evaluator;
import lombok.Getter;

@Getter
public class Population {

  private final List<EvaluatedIndividual> evaluatedIndividuals;

  private final List<Individual> elite;
  private final List<Individual> average;
  private final List<Individual> worst;

  public Population(List<Individual> individualList, Evaluator evaluator, GAParameters gaParams) {
    evaluatedIndividuals = individualList.parallelStream()
        .map(ind -> EvaluatedIndividual.builder()
            .individual(ind)
            .layout(evaluator.eval(ind))
            .build()
        ).sorted((o1, o2) -> evaluator.getObjectiveValueComparator()
            .compare(o1.getLayout(), o2.getLayout()))
        .collect(Collectors.toUnmodifiableList());

    elite = evaluatedIndividuals.subList(
            0,
            gaParams.getCounts().getElite())
        .stream().map(EvaluatedIndividual::getIndividual).collect(Collectors.toList());
    average = evaluatedIndividuals.subList(
            elite.size(),
            elite.size() + gaParams.getCounts().getAverage())
        .stream().map(EvaluatedIndividual::getIndividual).collect(Collectors.toList());
    worst = evaluatedIndividuals.subList(
            Math.min(elite.size() + average.size(), evaluatedIndividuals.size()) - 1,
            evaluatedIndividuals.size())
        .stream().map(EvaluatedIndividual::getIndividual).collect(Collectors.toList());
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
