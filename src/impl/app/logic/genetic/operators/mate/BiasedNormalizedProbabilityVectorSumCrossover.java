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

public class BiasedNormalizedProbabilityVectorSumCrossover implements BiasedMatingOperator {

  private final NormalizedProbabilityVectorSumCrossoverParameters params;

  public BiasedNormalizedProbabilityVectorSumCrossover(
      NormalizedProbabilityVectorSumCrossoverParameters params) {
    this.params = params;
  }


  @Override
  public Individual mate(Individual p1, Individual p2, double w1, double w2) {
    return Individual.builder()
        .paintingSeq(p1.getPaintingSeq())
        .paintingSeqRandomKey(computeBiasedPaintingSeqNormalizedSum(p1, p2, w1, w2))
        .slicingOrderRandomKey(computeBiasedSlicingOrderNormalizedSum(p1, p2, w1, w2))
        .orientationProb(computeBiasedOrientationProbabilities(p1, p2, w1, w2))
        .build();
  }

  @Override
  public Type getType() {
    return NORMALIZED_PROBABILITY_VECTOR_SUM_CROSSOVER;
  }

  private List<Double> computeBiasedPaintingSeqNormalizedSum(Individual p1, Individual p2,
      double w1, double w2) {
    return normalizeToProbabilityVector(
        sumVector(
            multVector(p1.getPaintingSeqRandomKey(), w1),
            multVector(p2.getPaintingSeqRandomKey(), w2)));
  }

  private List<Double> computeBiasedSlicingOrderNormalizedSum(Individual p1, Individual p2,
      double w1, double w2) {
    return normalizeToProbabilityVector(
        sumVector(
            multVector(p1.getSlicingOrderRandomKey(), w1),
            multVector(p2.getSlicingOrderRandomKey(), w2)));
  }


  private List<OrientationProbability> computeBiasedOrientationProbabilities(Individual p1,
      Individual p2, double w1, double w2) {
    List<OrientationProbability> res = new ArrayList<>(p1.getOrientationProb().size());
    for (int i = 0; i < p1.getOrientationProb().size(); i++) {
      List<Double> v1 = multVector(p1.getOrientationProb().get(i).getProbabilityVector(), w1);
      List<Double> v2 = multVector(p2.getOrientationProb().get(i).getProbabilityVector(), w2);
      res.add(new OrientationProbability(
          normalizeToProbabilityVector(multVector(sumVector(v1, v2), params.getWeights()))));
    }
    return res;
  }


}
