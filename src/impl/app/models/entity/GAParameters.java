package models.entity;

import logic.genetic.operators.mate.BiasedMatingOperator;
import logic.genetic.operators.mutate.MutateOperator;
import logic.genetic.operators.select.SelectOperator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GAParameters {

  private Integer maxNumberOfIter;
  private Integer populationSize;

  private PopulationDivisionCounts counts;
  private InitialPopulationDivisionCounts initialCounts;

  private BiasedMatingOperator matingOperator;
  private MutateOperator mutateOperator;
  private SelectOperator selectOperator;
}
