package factory;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import logic.metric.Metric;
import logic.objective.Objective;
import logic.objective.UseOnlyMetricObjective;
import models.dto.CreateComputationDto;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ObjectiveFactory implements Factory<CreateComputationDto, Objective> {

  private final MetricFactory metricFactory;

  @Inject
  public ObjectiveFactory(MetricFactory metricFactory) {
    this.metricFactory = metricFactory;
  }

  @Override
  public Objective create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return createForNames(dto.getGaParams().getObjective(), dto.getGaParams().getMetric());
  }

  public Objective createForNames(String objectiveName, String metricName)
      throws EntityNotFoundException, ImplementationNotFoundException {
    Objective.Type objectiveType = findOrThrow(objectiveName);
    Metric metric = metricFactory.create(metricName);

    switch (objectiveType) {
      case USE_METRIC_ONLY:
        return new UseOnlyMetricObjective(metric);
      default:
        throw new ImplementationNotFoundException(Objective.class, objectiveName);
    }
  }

  private Objective.Type findOrThrow(String name) throws EntityNotFoundException {
    return Objective.Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(Objective.class, name));
  }
}
