package logic.objective;

import java.util.List;
import java.util.Optional;
import logic.metric.Metric;
import models.entity.Painting;
import utils.EnumTypeInterface;
import utils.JavaUtils;

public interface Objective {

  Double eval(Painting p1, Painting p2);

  default Double eval(List<Painting> paintingSeq) {
    double value = 0d;
    for (int i = 0; i < paintingSeq.size(); i++) {
      for (int j = i + 1; j < paintingSeq.size(); j++) {
        value += eval(paintingSeq.get(i), paintingSeq.get(j));
      }
    }
    return value;
  }

  Type getType();

  Metric getMetric();

  enum Type implements EnumTypeInterface {
    USE_METRIC_ONLY("useMetricOnly");

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
