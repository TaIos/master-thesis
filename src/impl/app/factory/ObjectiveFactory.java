package factory;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.metric.Metric;
import logic.objective.Objective;
import logic.objective.UseOnlyMetricObjective;
import models.dto.CreateComputationDto;

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
    Objective.Type objectiveType = findOrThrow(dto.getGaParams().getObjective());
    Metric metric = metricFactory.create(dto.getGaParams().getMetric());

    if (objectiveType == Objective.Type.USE_METRIC_ONLY) {
      return new UseOnlyMetricObjective(metric);
    }
    throw new ImplementationNotFoundException(Objective.class, dto.getGaParams().getObjective());
  }

  private Objective.Type findOrThrow(String name) throws EntityNotFoundException {
    return Objective.Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(Objective.class, name));
  }
}
