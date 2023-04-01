package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.metric.EuclideanMetric;

@Singleton
public class EuclideanMetricProvider implements Provider<EuclideanMetric> {

  private final EuclideanMetric euclideanMetric;

  public EuclideanMetricProvider() {
    euclideanMetric = new EuclideanMetric();
  }

  @Override
  public EuclideanMetric get() {
    return euclideanMetric;
  }
}
