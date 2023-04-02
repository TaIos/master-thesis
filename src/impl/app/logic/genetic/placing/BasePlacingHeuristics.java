package logic.genetic.placing;

import java.util.ArrayList;
import java.util.List;
import logic.objective.Objective;
import logic.objective.ObjectiveValueComparator;
import models.entity.Painting;
import models.entity.PaintingPlacement;
import models.entity.PaintingsFlow;
import models.entity.PlacedSlicingLayout;
import models.entity.Point;
import models.entity.Rectangle;
import models.entity.SlicingLayout;

public abstract class BasePlacingHeuristics implements PlacingHeuristics {

  protected abstract List<Point> createPointsToTry(Painting painting, Rectangle allocatedSpace);

  @Override
  public PlacedSlicingLayout place(SlicingLayout slicingLayout, Objective objective,
      PaintingsFlow flow) {
    assert (slicingLayout.getAllocatedSpace().size() == slicingLayout.getPaintings().size());
    PlacedSlicingLayout placedSlicingLayout = new PlacedSlicingLayout(
        new ArrayList<>(slicingLayout.getPaintings().size()));

    for (int i = 0; i < slicingLayout.getPaintings().size(); i++) {
      Painting painting = slicingLayout.getPaintings().get(i);
      Rectangle allocatedSpace = slicingLayout.getAllocatedSpace().get(i);
      Point placement = place(painting, allocatedSpace, placedSlicingLayout, objective, flow);

      placedSlicingLayout.getPlacements().add(
          createPlacement(
              placement.getX(),
              placement.getY(),
              painting,
              allocatedSpace));
    }

    return placedSlicingLayout;
  }


  private Point place(Painting painting,
      Rectangle allocatedSpace,
      PlacedSlicingLayout placedSlicingLayout,
      Objective objective,
      PaintingsFlow flow) {
    List<Point> pointsToTry = createPointsToTry(painting, allocatedSpace);

    PaintingPlacement bestPlacement = createPlacement(
        pointsToTry.get(0).getX(),
        pointsToTry.get(0).getY(),
        painting,
        allocatedSpace
    );
    double bestObj = pointsToTry.size() > 1
        ? objective.peek(placedSlicingLayout, bestPlacement, flow)
        : ObjectiveValueComparator.OBJECTIVE_VALUE_MAX;

    for (int i = 1; i < pointsToTry.size(); i++) {
      Point point = pointsToTry.get(i);
      PaintingPlacement placement = createPlacement(
          point.getX(), point.getY(),
          painting, allocatedSpace
      );
      double obj = objective.peek(placedSlicingLayout, placement, flow);
      if (obj < bestObj) {
        bestPlacement = placement;
        bestObj = obj;
      }
    }

    return placementToPoint(bestPlacement);
  }

  private PaintingPlacement createPlacement(int x, int y, Painting painting,
      Rectangle allocatedSpace) {
    return PaintingPlacement.builder()
        .painting(painting)
        .allocatedSpace(allocatedSpace)
        .placement(Rectangle.builder()
            .x(x)
            .y(y)
            .width(painting.getWidth())
            .height(painting.getHeight())
            .build())
        .build();
  }

  private Point placementToPoint(PaintingPlacement placement) {
    return Point.builder()
        .x(placement.getPlacement().getX())
        .y(placement.getPlacement().getY())
        .build();
  }

}
