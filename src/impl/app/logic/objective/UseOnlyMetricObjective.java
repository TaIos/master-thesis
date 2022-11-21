package logic.objective;

import logic.metric.Metric;
import models.entity.Facility;

public class UseOnlyMetricObjective extends BaseObjective {

  public UseOnlyMetricObjective(Metric metric) {
    super(metric);
  }

  @Override
  public Double eval(Facility f1, Facility f2) {
    return metric.eval(f1, f2);
  }

  @Override
  public Type getType() {
    return Type.USE_METRIC_ONLY;
  }
}
