package logic.objective;

import logic.metric.Metric;
import models.entity.Facility;
import models.entity.Flow;

public class MetricTimesFrequencyObjective extends BaseObjective {

  private final Flow flow;

  public MetricTimesFrequencyObjective(Metric metric, Flow flow) {
    super(metric);
    this.flow = flow;
  }

  @Override
  public Double eval(Facility f1, Facility f2) {
    return flow.between(f1, f2) * metric.eval(f1, f2);
  }

  @Override
  public Type getType() {
    return Type.METRIC_TIMES_FREQUENCY;
  }
}
