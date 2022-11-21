package models.entity;

import logic.genetic.operators.mate.MatingOperator;
import logic.genetic.operators.mutate.MutateOperator;
import logic.genetic.operators.select.SelectOperator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GAParameters {

  private Double mutationProb;
  private Double crossoverProb;
  private Integer maxNumberOfIter;
  private Integer populationSize;

  private MatingOperator matingOperator;
  private MutateOperator mutateOperator;
  private SelectOperator selectOperator;
}
