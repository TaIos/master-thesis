package logic.genetic.resolvers;

import java.util.List;
import java.util.stream.Collectors;
import models.entity.Individual;
import models.entity.ResolvedIndividual;

public class IndividualResolver {

  private final OrientationResolver orientationResolver;
  private final RandomKeyDecoder randomKeyDecoder;

  public IndividualResolver(OrientationResolver orientationResolver,
      RandomKeyDecoder randomKeyDecoder) {
    this.orientationResolver = orientationResolver;
    this.randomKeyDecoder = randomKeyDecoder;
  }

  public List<ResolvedIndividual> resolve(Individual ind) {
    return orientationResolver.resolveAllPossibleCombinationsFromProbs(ind.getOrientationProb())
        .stream().map(orientationResolved -> ResolvedIndividual.builder()
            .paintingSeqResolved(
                randomKeyDecoder.decode(ind.getPaintingSeq(), ind.getPaintingSeqRandomKey()))
            .slicingOrderResolved(randomKeyDecoder.decode(ind.getSlicingOrderRandomKey()))
            .orientationResolved(orientationResolved)
            .build()).collect(Collectors.toList());
  }

}
