package logic.genetic.operators.select;

import models.entity.Individual;

import java.util.ArrayList;
import java.util.List;

public class SelectBestOperator implements SelectOperator {
  @Override
  public List<Individual> select(List<Individual> individuals, Integer k) {
    return new ArrayList<>(individuals.subList(0, k));
  }

  @Override
  public Type getType() {
    return Type.BEST;
  }
}
