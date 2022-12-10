package factory;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import javax.inject.Singleton;
import logic.metric.DummyMetric;
import logic.metric.EuclideanMetric;
import logic.metric.Metric;

@Singleton
public class MetricFactory implements Factory<String, Metric> {

  @Override
  public Metric create(String name)
      throws EntityNotFoundException, ImplementationNotFoundException {

    switch (findOrThrow(name)) {
      case DUMMY:
        return new DummyMetric();
      case EUCLIDEAN:
        return new EuclideanMetric();
      default:
        throw new ImplementationNotFoundException(Metric.class, name);
    }
  }

  private Metric.Type findOrThrow(String name) throws EntityNotFoundException {
    return Metric.Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(Metric.class, name));
  }
}
