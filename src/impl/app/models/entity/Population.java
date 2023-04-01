package models.entity;


import static logic.objective.ObjectiveValueComparator.OBJECTIVE_VALUE_MAX;

import java.util.List;
import java.util.stream.Collectors;
import logic.genetic.evaluator.Evaluator;
import lombok.Getter;

@Getter
public class Population {

  private final List<EvaluatedIndividual> evaluatedIndividuals;

  private final List<EvaluatedIndividual> eliteEvaluated;
  private final List<EvaluatedIndividual> averageEvaluated;
  private final List<EvaluatedIndividual> worstEvaluated;

  private final List<Individual> elite;
  private final List<Individual> average;
  private final List<Individual> worst;

  public Population(List<Individual> individualList, Evaluator evaluator,
      PopulationDivisionCounts counts) {
    evaluatedIndividuals = individualList.parallelStream()
        .map(ind -> EvaluatedIndividual.builder()
            .individual(ind)
            .layout(evaluator.eval(ind))
            .build()
        ).sorted((o1, o2) -> evaluator.getObjectiveValueComparator()
            .compare(o1.getLayout(), o2.getLayout()))
        .collect(Collectors.toUnmodifiableList());

    eliteEvaluated = evaluatedIndividuals.subList(
        0,
        counts.getElite());
    averageEvaluated = evaluatedIndividuals.subList(
        eliteEvaluated.size(),
        eliteEvaluated.size() + counts.getAverage());
    worstEvaluated = evaluatedIndividuals.subList(
        Math.min(eliteEvaluated.size() + averageEvaluated.size(), evaluatedIndividuals.size()) - 1,
        evaluatedIndividuals.size());

    elite = eliteEvaluated.stream().map(EvaluatedIndividual::getIndividual)
        .collect(Collectors.toList());
    average = averageEvaluated.stream().map(EvaluatedIndividual::getIndividual)
        .collect(Collectors.toList());
    worst = worstEvaluated.stream().map(EvaluatedIndividual::getIndividual)
        .collect(Collectors.toList());
  }

  public Population(List<Individual> individualList, Evaluator evaluator) {
    this(individualList,
        evaluator,
        PopulationDivisionCounts.builder()
            .elite(1).average(1).worst(1).children(1).mutant(1).winner(1).random(1)
            .build()
    );
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
