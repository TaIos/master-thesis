package factory.copy_factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.entity.Individual;
import models.entity.OrientationProbability;

@Singleton
public class IndividualCopyFactory implements CopyFactory<Individual> {

  private final OrientationProbabilityCopyFactory orientationProbabilityCopyFactory;


  @Inject
  public IndividualCopyFactory(
      OrientationProbabilityCopyFactory orientationProbabilityCopyFactory) {
    this.orientationProbabilityCopyFactory = orientationProbabilityCopyFactory;
  }

  @Override
  public Individual createCopy(Individual ind) {
    return Individual.builder()
        .paintingSeq(new ArrayList<>(ind.getPaintingSeq()))
        .paintingSeqRandomKey(new ArrayList<>(ind.getPaintingSeqRandomKey()))
        .slicingOrderRandomKey(new ArrayList<>(ind.getSlicingOrderRandomKey()))
        .orientationProb(createOrientationProbCopy(ind))
        .build();
  }

  private List<OrientationProbability> createOrientationProbCopy(Individual ind) {
    return ind.getOrientationProb().stream()
        .map(orientationProbabilityCopyFactory::createCopy)
        .collect(Collectors.toList());
  }
}
