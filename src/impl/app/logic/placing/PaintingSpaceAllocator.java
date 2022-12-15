package logic.placing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import logic.genetic.RandomKeyDecoder;
import models.entity.Individual;
import models.entity.Orientation;
import models.entity.Painting;
import models.entity.Rectangle;
import services.RectangleService;

public class PaintingSpaceAllocator {

  private final RectangleService rectangleService;
  private final RandomKeyDecoder decoder;

  public PaintingSpaceAllocator(RectangleService rectangleService, RandomKeyDecoder decoder) {
    this.rectangleService = rectangleService;
    this.decoder = decoder;
  }


  public void computeAndSetPaintingAllocatedSpace(Individual individual, List<Orientation> orientations,
      Rectangle layoutBoundingRectangle) {
    computeAndSetRecursively(
        decoder.decodePaintingSequence(individual),
        decoder.decodeSlicingOrder(individual),
        orientations,
        layoutBoundingRectangle);
  }

  private void computeAndSetRecursively(
      List<Painting> paintings,
      List<Integer> slicingOrder,
      List<Orientation> orientations,
      Rectangle rec) {
    if (paintings.size() == 1) {
      paintings.get(0).setAllocatedSpace(rec);
      return;
    }

    int k = slicingOrder.get(0);
    List<Painting> left = paintings.subList(0, k);
    List<Painting> right = paintings.subList(k, paintings.size());
    List<Rectangle> sp = createSplit(orientations.get(0), rec, left, right);

    computeAndSetRecursively(
        left,
        createNextSlicingOrderUpTo(slicingOrder, k),
        createNextOrientationUpTo(slicingOrder, orientations, k),
        sp.get(0));
    computeAndSetRecursively(
        right,
        createNextSlicingOrderAfter(slicingOrder, k),
        createNextOrientationAfter(slicingOrder, orientations, k),
        sp.get(1));
  }

  private List<Rectangle> createSplit(
      Orientation orientation, Rectangle layout, List<Painting> left, List<Painting> right) {
    double leftArea = left.stream().mapToDouble(Painting::getArea).sum();
    double rightArea = right.stream().mapToDouble(Painting::getArea).sum();
    double ratio = leftArea / (leftArea + rightArea);
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
      if (pred.test(slicingOrderIt.next())) {
        result.add(orientation);
      }
    }
    return result;
  }
}