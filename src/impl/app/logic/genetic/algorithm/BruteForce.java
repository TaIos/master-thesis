package logic.genetic.algorithm;

import static logic.genetic.algorithm.GeneticAlgorithm.Type.BRUTE;

import java.util.List;
import java.util.Random;
import logic.genetic.Generator;
import logic.genetic.HallOfFame;
import logic.genetic.evaluator.Evaluator;
import models.entity.EvaluatedSlicingLayout;
import models.entity.GAParameters;
import models.entity.GAResult;
import models.entity.InstanceParameters;
import models.entity.PaintingPlacement;
import models.entity.Rectangle;
import org.slf4j.Logger;

public class BruteForce extends BaseGeneticAlgorithm {


  public BruteForce(
      GAParameters gaParams,
      InstanceParameters instanceParams,
      Evaluator evaluator,
      HallOfFame hof,
      Generator generator,
      Random rnd,
      Logger logger) {
    super(gaParams, instanceParams, evaluator, hof, generator, rnd, logger);
  }

  @Override
  public GAResult call() {
    EvaluatedSlicingLayout dummy = EvaluatedSlicingLayout.builder()
        .placements(List.of(
            PaintingPlacement.builder()
                .allocatedSpace(Rectangle.builder().x(1).y(1).width(1).height(1).build())
                .placement(Rectangle.builder().x(1).y(1).width(1).height(1).build())
                .build())
        )
        .objectiveValue(-1d)
        .build();
    return GAResult.builder()
        .hallOfFame(hof)
        .layout(dummy)
        .build();
  }

  @Override
  public Type getType() {
    return BRUTE;
  }
}
