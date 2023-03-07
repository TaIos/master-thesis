package logic.genetic.evaluator;

import static logic.genetic.evaluator.Evaluator.Type.GENETIC;

import java.util.NoSuchElementException;
import logic.genetic.algorithm.PlacingHeuristics;
import logic.genetic.resolvers.IndividualResolver;
import logic.objective.Objective;
import logic.objective.ObjectiveValueComparator;
import logic.placing.PaintingSpaceAllocator;
import models.entity.EvaluatedSlicingLayout;
import models.entity.Individual;
import models.entity.InstanceParameters;

public class GaEvaluator implements Evaluator {

  private final IndividualResolver individualResolver;
  private final PaintingSpaceAllocator paintingSpaceAllocator;
  private final PlacingHeuristics placingHeuristics;
  private final Objective objective;
  private final ObjectiveValueComparator objectiveValueComparator;

  private final InstanceParameters params;


  public GaEvaluator(IndividualResolver individualResolver,
      PaintingSpaceAllocator paintingSpaceAllocator,
      PlacingHeuristics placingHeuristics,
      Objective objective,
      ObjectiveValueComparator objectiveValueComparator, InstanceParameters params) {
    this.individualResolver = individualResolver;
    this.paintingSpaceAllocator = paintingSpaceAllocator;
    this.placingHeuristics = placingHeuristics;
    this.objective = objective;
    this.objectiveValueComparator = objectiveValueComparator;
    this.params = params;
  }

  @Override
  public EvaluatedSlicingLayout eval(Individual ind) {
    return individualResolver.resolve(ind).stream()
        .map(resolvedIndividual -> paintingSpaceAllocator.createSlicingLayout(resolvedIndividual,
            params.getLayout().getBoundingRectangle()))
        .map(placingHeuristics::place)
        .map(objective::eval)
        .min(objectiveValueComparator)
        .orElseThrow(() -> new NoSuchElementException("There is no individual resolved"));
  }

  @Override
  public ObjectiveValueComparator getObjectiveValueComparator() {
    return objectiveValueComparator;
  }

  @Override
  public Type getType() {
    return GENETIC;
  }

}