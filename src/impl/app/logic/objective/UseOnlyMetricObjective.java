package logic.objective;

import logic.metric.Metric;
import models.entity.Painting;

public class UseOnlyMetricObjective extends BaseObjective {

  public UseOnlyMetricObjective(Metric metric) {
    super(metric);
  }

  @Override
  public Double eval(Painting p1, Painting p2) {
    return metric.eval(p1, p2);
  }

  @Override
  public Type getType() {
    return Type.USE_METRIC_ONLY;
  }
}
