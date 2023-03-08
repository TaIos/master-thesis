package logic.genetic.algorithm;

import static logic.genetic.algorithm.GeneticAlgorithm.Type.PROBABILISTIC_BRUTE;
import static logic.objective.ObjectiveValueComparator.OBJECTIVE_VALUE_MAX;
import static utils.DelayedFormatter.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import logic.genetic.Generator;
import logic.genetic.HallOfFame;
import logic.genetic.evaluator.Evaluator;
import logic.objective.Objective;
import logic.objective.ObjectiveValueComparator;
import models.entity.EvaluatedSlicingLayout;
import models.entity.GAParameters;
import models.entity.GAResult;
import models.entity.InstanceParameters;
import models.entity.Painting;
import models.entity.PaintingPlacement;
import models.entity.PlacedSlicingLayout;
import models.entity.Point;
import models.entity.Rectangle;
import org.slf4j.Logger;

public class ProbabilisticBruteForce extends BaseGeneticAlgorithm {

  private final Objective objective;
  private final ObjectiveValueComparator objectiveValueComparator;

  public ProbabilisticBruteForce(
      GAParameters gaParams,
      InstanceParameters instanceParams,
      Evaluator evaluator,
      HallOfFame hof,
      Generator generator,
      Random rnd,
      Logger logger,
      Objective objective,
      ObjectiveValueComparator objectiveValueComparator) {
    super(gaParams, instanceParams, evaluator, hof, generator, rnd, logger);
    this.objective = objective;
    this.objectiveValueComparator = objectiveValueComparator;
  }

  // TODO if time: reverse engineer the slicing tree and some random keys
  @Override
  public GAResult call() {
    EvaluatedSlicingLayout bestLayout = EvaluatedSlicingLayout.builder()
        .objectiveValue(OBJECTIVE_VALUE_MAX).build();
    int bestIter = 0;
    List<Point> points = computePossiblePlacementPoints();

    for (int iter = 0; iter < gaParams.getMaxNumberOfIter(); iter++) {
      Collections.shuffle(points, rnd);
      EvaluatedSlicingLayout layout = objective.eval(
          new PlacedSlicingLayout(createPlacements(points)));
      if (objectiveValueComparator.compare(layout, bestLayout) < 0) {
        bestLayout = layout;
        bestIter = iter;
      }
      logger.info("iteration={}/{}, best={} (iter={})",
          iter,
          gaParams.getMaxNumberOfIter(),
          format("%.02f", bestLayout.getObjectiveValue()),
          bestIter);
    }
    return new GAResult(hof, bestLayout);
  }

  private List<Point> computePossiblePlacementPoints() {
    Rectangle boundingRec = instanceParams.getLayout().getBoundingRectangle();
    List<Point> points = new ArrayList<>(boundingRec.getHeight() * boundingRec.getWidth());
    for (int i = 0; i < boundingRec.getWidth(); i++) {
      for (int j = 0; j < boundingRec.getHeight(); j++) {
        points.add(Point.builder()
            .x(i).y(j)
            .build());
      }
    }
    return points;
  }

  private List<PaintingPlacement> createPlacements(List<Point> points) {
    List<PaintingPlacement> placements = new ArrayList<>(instanceParams.getPaintings().size());
    for (int i = 0; i < points.size() && i < instanceParams.getPaintings().size(); i++) {
      Point point = points.get(i);
      Painting painting = instanceParams.getPaintings().get(i);
      Rectangle rec = Rectangle.builder()
          .x(point.getX())
          .y(point.getY())
          .width(painting.getWidth())
          .height(painting.getHeight())
          .build();
      placements.add(PaintingPlacement.builder()
          .placement(rec)
          .allocatedSpace(rec)
          .build());
    }
    return placements;
  }

  @Override
  public Type getType() {
    return PROBABILISTIC_BRUTE;
  }
}
