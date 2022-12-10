package logic.genetic.operators.mate;

import static logic.genetic.operators.mate.MatingOperator.Type.ONE_POINT_FULL_CROSSOVER;
import static utils.JavaUtils.concatWithPreserveOrder;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import models.entity.Individual;
import models.entity.Orientation;
import models.entity.Painting;

public class OnePointFullCrossover implements MatingOperator {

  private final Random rnd;

  public OnePointFullCrossover(Random rnd) {
    this.rnd = rnd;
  }

  @Override
  public List<Individual> mate(Individual p1, Individual p2) {
    int k = rnd.nextInt(p1.getSlicingOrderRandomKey().size());
    List<Double> f1 = p1.getPaintingSeqRandomKey().subList(0, k);
    List<Double> f2 =
        p2.getPaintingSeqRandomKey().subList(k, p2.getPaintingSeqRandomKey().size());
    List<Double> slice1 = p1.getSlicingOrderRandomKey().subList(0, k);
    List<Double> slice2 =
        p2.getSlicingOrderRandomKey().subList(k, p2.getSlicingOrderRandomKey().size());
    List<Orientation> orient1 = p1.getOrientations().subList(0, k);
    List<Orientation> orient2 = p2.getOrientations().subList(k, p2.getOrientations().size());

    return List.of(
        create(f1, f2, slice1, slice2, orient1, orient2, p1),
        create(f2, f1, slice2, slice1, orient2, orient1, p2));
  }

  private Individual create(
      List<Double> f1,
      List<Double> f2,
      List<Double> slice1,
      List<Double> slice2,
      List<Orientation> orient1,
      List<Orientation> orient2,
      Individual copyTemplate) {
    return Individual.builder()
        .paintingSeq(
            copyTemplate.getPaintingSeq().stream()
                .map(Painting::clone)
                .collect(Collectors.toList()))
        .paintingSeqRandomKey(concatWithPreserveOrder(f1, f2))
        .slicingOrderRandomKey(concatWithPreserveOrder(slice1, slice2))
        .orientations(concatWithPreserveOrder(orient1, orient2))
        .build();
  }

  @Override
  public Type getType() {
    return ONE_POINT_FULL_CROSSOVER;
  }
}
