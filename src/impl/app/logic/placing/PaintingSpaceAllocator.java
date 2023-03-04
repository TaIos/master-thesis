package logic.placing;

import static utils.JavaUtils.concatWithPreservedOrder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import models.entity.Painting;
import models.entity.Rectangle;
import models.entity.ResolvedIndividual;
import models.entity.ResolvedOrientation;
import models.entity.ResolvedOrientation.ResolvedOrientationType;
import models.entity.SlicingLayout;
import services.RectangleService;

public class PaintingSpaceAllocator {

  private final RectangleService rectangleService;

  public PaintingSpaceAllocator(RectangleService rectangleService) {
    this.rectangleService = rectangleService;
  }


  public SlicingLayout createSlicingLayout(ResolvedIndividual resInd, Rectangle boundingRectangle) {
    return SlicingLayout.builder()
        .paintings(resInd.getPaintingSeqResolved())
        .allocatedSpace(
            computeRecursively(
                resInd.getPaintingSeqResolved(),
                resInd.getSlicingOrderResolved(),
                resInd.getOrientationResolved(),
                boundingRectangle))
        .build();
  }

  private List<Rectangle> computeRecursively(
      List<Painting> paintings,
      List<Integer> slicingOrder,
      List<ResolvedOrientation> orientations,
      Rectangle rec) {
    if (paintings.size() == 1) {
      return List.of(rec);
    }

    int k = slicingOrder.get(0);
    List<Painting> left = paintings.subList(0, k);
    List<Painting> right = paintings.subList(k, paintings.size());
    List<Rectangle> sp = createSplit(orientations.get(0), rec, left, right);

    return concatWithPreservedOrder(
        computeRecursively(
            left,
            createNextSlicingOrderUpTo(slicingOrder, k),
            createNextOrientationUpTo(slicingOrder, orientations, k),
            sp.get(0)),
        computeRecursively(
            right,
            createNextSlicingOrderAfter(slicingOrder, k),
            createNextOrientationAfter(slicingOrder, orientations, k),
            sp.get(1)));
  }

  private List<Rectangle> createSplit(
      ResolvedOrientation orientation, Rectangle layout, List<Painting> left,
      List<Painting> right) {
    double leftArea = left.stream().mapToDouble(Painting::getArea).sum();
    double rightArea = right.stream().mapToDouble(Painting::getArea).sum();
    double ratio = leftArea / (leftArea + rightArea);
    return orientation.getType().equals(ResolvedOrientationType.HORIZONTAL)
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

  private List<ResolvedOrientation> createNextOrientationUpTo(
      List<Integer> slicingOrder, List<ResolvedOrientation> orientations, int k) {
    return createNextOrientation(slicingOrder, orientations, val -> val < k);
  }

  private List<ResolvedOrientation> createNextOrientationAfter(
      List<Integer> slicingOrder, List<ResolvedOrientation> orientations, int k) {
    return createNextOrientation(slicingOrder, orientations, val -> val > k);
  }

  private List<ResolvedOrientation> createNextOrientation(
      List<Integer> slicingOrder, List<ResolvedOrientation> orientations, Predicate<Integer> pred) {
    Iterator<Integer> slicingOrderIt = slicingOrder.iterator();
    Iterator<ResolvedOrientation> orientationIt = orientations.iterator();
    List<ResolvedOrientation> result = new ArrayList<>();

    while (slicingOrderIt.hasNext() && orientationIt.hasNext()) {
      ResolvedOrientation orientation = orientationIt.next();
      if (pred.test(slicingOrderIt.next())) {
        result.add(orientation);
      }
    }
    return result;
  }
}
