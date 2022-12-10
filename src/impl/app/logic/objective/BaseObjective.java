package logic.objective;

import logic.metric.Metric;

public abstract class BaseObjective implements Objective {

  protected final Metric metric;

  BaseObjective(Metric metric) {
    this.metric = metric;
  }

  public Metric getMetric() {
    return metric;
  }
}
