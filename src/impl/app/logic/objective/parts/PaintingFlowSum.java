package logic.objective.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import logic.metric.Metric;
import models.entity.PaintingPlacement;
import models.entity.PaintingsFlow;
import models.entity.Rectangle;
import org.apache.commons.lang3.tuple.Pair;

public class PaintingFlowSum {

  public double calculate(List<PaintingPlacement> placements, PaintingsFlow flow, Metric metric) {
    List<Pair<PaintingPlacement, PaintingPlacement>> pairs = new ArrayList<>(placements.size());
    for (int i = 0; i < placements.size(); i++) {
      for (int j = i + 1; j < placements.size(); j++) {
        // flow is considered to be symmetric
        pairs.add(Pair.of(placements.get(i), placements.get(j)));
      }
    }
    return calculateParallel(pairs, flow, metric);
  }

  public double calculateForOne(PaintingPlacement one, List<PaintingPlacement> placements,
      PaintingsFlow flow,
      Metric metric) {
    return calculateParallel(placements.stream()
        // flow is considered to be symmetric
        .map(p -> Pair.of(p, one))
        .collect(Collectors.toList()), flow, metric);
  }

  private double calculateParallel(List<Pair<PaintingPlacement, PaintingPlacement>> pairs,
      PaintingsFlow flow,
      Metric metric) {
    return pairs.parallelStream()
        .mapToDouble(
            pair -> flow.between(pair.getLeft().getPainting(), pair.getRight().getPainting())
                * metricCenter(pair.getLeft().getPlacement(), pair.getRight().getPlacement(),
                metric)
        ).sum();
  }

  private double metricCenter(Rectangle r1, Rectangle r2, Metric metric) {
    return metric.calculate(
        r1.getX() + r1.getWidth() / 2.0,
        r1.getY() + r1.getHeight() / 2.0,
        r2.getX() + r1.getWidth() / 2.0,
        r2.getY() + r1.getHeight() / 2.0
    );
  }


}
