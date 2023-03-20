package models.entity;


import static logic.objective.ObjectiveValueComparator.OBJECTIVE_VALUE_MAX;

import java.util.List;
import java.util.stream.Collectors;
import logic.genetic.evaluator.Evaluator;
import lombok.Getter;

@Getter
public class Population {

  private final List<EvaluatedIndividual> evaluatedIndividuals;

  private final List<EvaluatedIndividual> elite;
  private final List<EvaluatedIndividual> average;
  private final List<EvaluatedIndividual> worst;

  public Population(List<Individual> individualList, Evaluator evaluator) {
    evaluatedIndividuals = individualList.parallelStream()
        .map(ind -> EvaluatedIndividual.builder()
            .individual(ind)
            .layout(evaluator.eval(ind))
            .build()
        ).sorted((o1, o2) -> evaluator.getObjectiveValueComparator()
            .compare(o1.getLayout(), o2.getLayout()))
        .collect(Collectors.toUnmodifiableList());

    // TODO propagate percentages
    elite = evaluatedIndividuals.subList(
        0,
        (int) (1 + evaluatedIndividuals.size() * 0.2));
    average = evaluatedIndividuals.subList(
        elite.size(),
        Math.min((int) (1 + elite.size() + evaluatedIndividuals.size() * 0.6),
            evaluatedIndividuals.size()));
    worst = evaluatedIndividuals.subList(
        Math.min(elite.size() + average.size(), evaluatedIndividuals.size()) - 1,
        evaluatedIndividuals.size());
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
