package logic.genetic.operators.select;

import java.util.ArrayList;
import java.util.List;
import models.entity.Individual;

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
