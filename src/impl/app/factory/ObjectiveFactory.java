package factory;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import logic.metric.Metric;
import logic.objective.MetricTimesFrequencyObjective;
import logic.objective.Objective;
import logic.objective.UseOnlyMetricObjective;
import models.dto.CreateComputationDto;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ObjectiveFactory implements Factory<CreateComputationDto, Objective> {

  private final MetricFactory metricFactory;
  private final FlowFactory flowFactory;

  @Inject
  public ObjectiveFactory(MetricFactory metricFactory, FlowFactory flowFactory) {
    this.metricFactory = metricFactory;
    this.flowFactory = flowFactory;
  }

  @Override
  public Objective create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    Objective.Type objectiveType = findOrThrow(dto.getGaParams().getObjective());
    Metric metric = metricFactory.create(dto.getGaParams().getMetric());

    switch (objectiveType) {
      case USE_METRIC_ONLY:
        return new UseOnlyMetricObjective(metric);
      case METRIC_TIMES_FREQUENCY:
        return new MetricTimesFrequencyObjective(metric, flowFactory.create(dto.getInstanceParams().getFlow()));
      default:
        throw new ImplementationNotFoundException(Objective.class, dto.getGaParams().getObjective());
    }
  }

  private Objective.Type findOrThrow(String name) throws EntityNotFoundException {
    return Objective.Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(Objective.class, name));
  }
}
