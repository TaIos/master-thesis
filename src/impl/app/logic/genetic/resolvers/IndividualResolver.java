package logic.genetic.resolvers;

import java.util.List;
import java.util.stream.Collectors;
import models.entity.Individual;
import models.entity.ResolvedIndividual;

public class IndividualResolver {

  private final OrientationResolver orientationResolver;
  private final MaximumWildCardCountResolver maximumWildCardCountResolver;
  private final RandomKeyDecoder randomKeyDecoder;

  public IndividualResolver(OrientationResolver orientationResolver,
      MaximumWildCardCountResolver maximumWildCardCountResolver,
      RandomKeyDecoder randomKeyDecoder) {
    this.orientationResolver = orientationResolver;
    this.maximumWildCardCountResolver = maximumWildCardCountResolver;
    this.randomKeyDecoder = randomKeyDecoder;
  }

  public List<ResolvedIndividual> resolve(Individual ind, int maximumWildCardCount) {
    return orientationResolver.resolveAllPossibleCombinationsFromProbs(
            maximumWildCardCountResolver.resolve(ind, maximumWildCardCount)).stream()
        .map(orientationResolved -> ResolvedIndividual.builder()
            .paintingSeqResolved(
                randomKeyDecoder.decode(ind.getPaintingSeq(), ind.getPaintingSeqRandomKey()))
            .slicingOrderResolved(randomKeyDecoder.decode(ind.getSlicingOrderRandomKey()))
            .orientationResolved(orientationResolved)
            .build()).collect(Collectors.toList());
  }

}
