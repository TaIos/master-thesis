package logic.genetic;

import models.entity.Facility;
import models.entity.Individual;
import models.entity.Orientation;
import models.entity.Rectangle;
import services.RectangleService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Placing {

  private final RectangleService rectangleService;

  public Placing(RectangleService rectangleService) {
    this.rectangleService = rectangleService;
  }

  public void setFacilityLayout(Individual individual, Rectangle layout) {
    setRecursively(
        individual.getFacilitySequence(),
        individual.getSlicingOrder(),
        individual.getOrientations(),
        layout);
  }

  private void setRecursively(
      List<Facility> facilitySequence,
      List<Integer> slicingOrder,
      List<Orientation> orientations,
      Rectangle layout) {
    if (facilitySequence.size() == 1) {
      facilitySequence.get(0).setPlacement(layout);
      return;
    }

    int k = slicingOrder.get(0);
    List<Facility> lf = facilitySequence.subList(0, k);
    List<Facility> rf = facilitySequence.subList(k, facilitySequence.size());
    List<Rectangle> sp = createSplit(orientations.get(0), layout, lf, rf);

    setRecursively(
        lf,
        createNextSlicingOrderUpTo(slicingOrder, k),
        createNextOrientationUpTo(slicingOrder, orientations, k),
        sp.get(0));
    setRecursively(
        rf,
        createNextSlicingOrderAfter(slicingOrder, k),
        createNextOrientationAfter(slicingOrder, orientations, k),
        sp.get(1));
  }

  private List<Rectangle> createSplit(
      Orientation orientation, Rectangle layout, List<Facility> lf, List<Facility> rf) {
    double lfArea = lf.stream().mapToDouble(Facility::getArea).sum();
    double rfArea = rf.stream().mapToDouble(Facility::getArea).sum();
    double ratio = lfArea / (lfArea + rfArea);
    return orientation.getType().equals(Orientation.Type.HORIZONTAL)
        ? rectangleService.splitHorizontally(layout, ratio)
        : rectangleService.splitVertically(layout, ratio);
  }

  private List<Integer> createNextSlicingOrderUpTo(List<Integer> slicingOrder, int k) {
    return createNextSlicingOrder(slicingOrder, val -> val < k, 0);
  }

  private List<Integer> createNextSlicingOrderAfter(List<Integer> slicingOrder, int k) {
    return createNextSlicingOrder(slicingOrder, val -> val > k, k);
  }

  private List<Integer> createNextSlicingOrder(
      List<Integer> slicingOrder, Predicate<Integer> pred, int offset) {
    return slicingOrder.stream().filter(pred).map(val -> val - offset).collect(Collectors.toList());
  }

  private List<Orientation> createNextOrientationUpTo(
      List<Integer> slicingOrder, List<Orientation> orientations, int k) {
    return createNextOrientation(slicingOrder, orientations, val -> val < k);
  }

  private List<Orientation> createNextOrientationAfter(
      List<Integer> slicingOrder, List<Orientation> orientations, int k) {
    return createNextOrientation(slicingOrder, orientations, val -> val > k);
  }

  private List<Orientation> createNextOrientation(
      List<Integer> slicingOrder, List<Orientation> orientations, Predicate<Integer> pred) {
    Iterator<Integer> slicingOrderIt = slicingOrder.iterator();
    Iterator<Orientation> orientationIt = orientations.iterator();
    List<Orientation> result = new ArrayList<>();

    while (slicingOrderIt.hasNext() && orientationIt.hasNext()) {
      Orientation orientation = orientationIt.next();
      if (pred.test(slicingOrderIt.next())) result.add(orientation);
    }
    return result;
  }
}
