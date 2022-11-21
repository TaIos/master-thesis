package logic.genetic;

import models.entity.Facility;
import models.entity.Individual;
import models.entity.Orientation;
import models.entity.Rectangle;
import services.RectangleService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Singleton
public class Placing {

  private final RectangleService rectangleService;

  @Inject
  public Placing(RectangleService rectangleService) {
    this.rectangleService = rectangleService;
  }

  public void setFacilityGrid(Individual individual, Rectangle grid) {
    setRecursively(
        individual.getFacilitySequence(),
        individual.getSlicingOrder(),
        individual.getOrientations(),
        grid);
  }

  private void setRecursively(
      List<Facility> facilitySequence,
      List<Integer> slicingOrder,
      List<Orientation> orientations,
      Rectangle grid) {
    if (slicingOrder.isEmpty()) return;

    int k = slicingOrder.get(0);
    List<Facility> lf = facilitySequence.subList(0, k);
    List<Facility> rf = facilitySequence.subList(k, 0);
    List<Rectangle> sp = createSplit(orientations.get(0), grid);
    placeIfSingle(lf, rf, sp);

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

  private List<Rectangle> createSplit(Orientation orientation, Rectangle grid) {
    return orientation.getType().equals(Orientation.Type.HORIZONTAL)
        ? rectangleService.splitHorizontally(grid)
        : rectangleService.splitVertically(grid);
  }

  private void placeIfSingle(List<Facility> left, List<Facility> right, List<Rectangle> split) {
    placeIfSingle(left, split.get(0));
    placeIfSingle(right, split.get(1));
  }

  private void placeIfSingle(List<Facility> facilities, Rectangle rectangle) {
    if (facilities.size() == 1) {
      facilities.get(0).setPlacement(rectangle);
    }
  }

  private List<Integer> createNextSlicingOrderUpTo(List<Integer> slicingOrder, int k) {
    return createNextSlicingOrder(slicingOrder, val -> val < k);
  }

  private List<Integer> createNextSlicingOrderAfter(List<Integer> slicingOrder, int k) {
    return createNextSlicingOrder(slicingOrder, val -> val > k);
  }

  private List<Integer> createNextSlicingOrder(
      List<Integer> slicingOrder, Predicate<Integer> pred) {
    return slicingOrder.stream().filter(pred).collect(Collectors.toList());
  }

  private List<Orientation> createNextOrientationUpTo(
      List<Integer> slicingOrder, List<Orientation> orientations, int k) {
    return createNextOrientation(slicingOrder, orientations, (value) -> value < k);
  }

  private List<Orientation> createNextOrientationAfter(
      List<Integer> slicingOrder, List<Orientation> orientations, int k) {
    return createNextOrientation(slicingOrder, orientations, (value) -> value > k);
  }

  private List<Orientation> createNextOrientation(
      List<Integer> slicingOrder, List<Orientation> orientations, Predicate<Integer> pred) {
    Iterator<Integer> slicingOrderIt = slicingOrder.iterator();
    Iterator<Orientation> orientationsIterator = orientations.iterator();
    List<Orientation> orientationsNext = new ArrayList<>();

    while (slicingOrderIt.hasNext() && orientationsIterator.hasNext()) {
      Orientation orientation = orientationsIterator.next();
      if (pred.test(slicingOrderIt.next())) orientationsNext.add(orientation);
    }
    return orientationsNext;
  }
}
