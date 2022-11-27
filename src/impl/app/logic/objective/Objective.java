package logic.objective;

import logic.metric.Metric;
import models.entity.Facility;
import utils.EnumTypeInterface;
import utils.JavaUtils;

import java.util.List;
import java.util.Optional;

public interface Objective {

  Double eval(Facility f1, Facility f2);

  default Double eval(List<Facility> facilitySequence) {
    double value = 0d;
    for (int i = 0; i < facilitySequence.size(); i++) {
      for (int j = i + 1; j < facilitySequence.size(); j++) {
        value += eval(facilitySequence.get(i), facilitySequence.get(j));
      }
    }
    return value;
  }

  Type getType();

  Metric getMetric();

  enum Type implements EnumTypeInterface {
    USE_METRIC_ONLY("useMetricOnly"),
    METRIC_TIMES_FREQUENCY("metricTimesFrequency");

    final String label;

    Type(String label) {
      this.label = label;
    }

    public static Optional<Type> getForLabel(String label) {
      return JavaUtils.getForLabel(values(), label);
    }

    @Override
    public String getLabel() {
      return label;
    }
  }
}
