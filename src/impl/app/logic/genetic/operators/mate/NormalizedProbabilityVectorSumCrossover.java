package logic.genetic.operators.mate;

import static logic.genetic.operators.mate.MatingOperator.Type.NORMALIZED_PROBABILITY_VECTOR_SUM_CROSSOVER;
import static utils.JavaUtils.Vector.multVector;
import static utils.JavaUtils.Vector.normalizeToProbabilityVector;
import static utils.JavaUtils.Vector.sumVector;

import java.util.ArrayList;
import java.util.List;
import models.entity.Individual;
import models.entity.NormalizedProbabilityVectorSumCrossoverParameters;
import models.entity.OrientationProbability;

public class NormalizedProbabilityVectorSumCrossover implements MatingOperator {

  private final NormalizedProbabilityVectorSumCrossoverParameters params;

  public NormalizedProbabilityVectorSumCrossover(
      NormalizedProbabilityVectorSumCrossoverParameters params) {
    this.params = params;
  }


  @Override
  public Individual mate(Individual p1, Individual p2) {
    return Individual.builder()
        .paintingSeq(p1.getPaintingSeq())
        .paintingSeqRandomKey(computePaintingSeqNormalizedSum(p1, p2))
        .slicingOrderRandomKey(computeSlicingOrderNormalizedSum(p1, p2))
        .orientationProb(computeOrientationProbabilities(p1, p2))
        .build();
  }

  @Override
  public Type getType() {
    return NORMALIZED_PROBABILITY_VECTOR_SUM_CROSSOVER;
  }

  private List<Double> computePaintingSeqNormalizedSum(Individual p1, Individual p2) {
    return normalizeToProbabilityVector(
        sumVector(p1.getPaintingSeqRandomKey(), p2.getPaintingSeqRandomKey()));
  }

  private List<Double> computeSlicingOrderNormalizedSum(Individual p1, Individual p2) {
    return normalizeToProbabilityVector(
        sumVector(p1.getSlicingOrderRandomKey(), p2.getSlicingOrderRandomKey()));
  }


  private List<OrientationProbability> computeOrientationProbabilities(Individual p1,
      Individual p2) {
    List<OrientationProbability> res = new ArrayList<>(p1.getOrientationProb().size());
    for (int i = 0; i < p1.getOrientationProb().size(); i++) {
      List<Double> v1 = p1.getOrientationProb().get(i).getProbabilityVector();
      List<Double> v2 = p2.getOrientationProb().get(i).getProbabilityVector();
      List<Double> w = params.getWeights();
      res.add(new OrientationProbability(
          normalizeToProbabilityVector(multVector(sumVector(v1, v2), w))));
    }
    return res;
  }


}
