package logic.objective;


import java.util.Comparator;
import models.entity.EvaluatedSlicingLayout;

public class ObjectiveValueComparator implements Comparator<EvaluatedSlicingLayout> {

  public static final double OBJECTIVE_VALUE_MAX = Double.MAX_VALUE;

  public int compare(Double first, Double second) {
    return first.compareTo(second);
  }

  @Override
  public int compare(EvaluatedSlicingLayout first, EvaluatedSlicingLayout second) {
    return compare(first.getObjectiveValue(), second.getObjectiveValue());
  }
}
