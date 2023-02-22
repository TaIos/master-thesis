package factory.copy_factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.entity.Individual;
import models.entity.OrientationProbability;
import models.entity.Painting;

@Singleton
public class IndividualCopyFactory implements CopyFactory<Individual> {

  private final PaintingCopyFactory paintingCopyFactory;
  private final OrientationProbabilityCopyFactory orientationProbabilityCopyFactory;


  @Inject
  public IndividualCopyFactory(PaintingCopyFactory paintingCopyFactory,
      OrientationProbabilityCopyFactory orientationProbabilityCopyFactory) {
    this.paintingCopyFactory = paintingCopyFactory;
    this.orientationProbabilityCopyFactory = orientationProbabilityCopyFactory;
  }

  @Override
  public Individual createCopy(Individual ind) {
    return Individual.builder()
        .paintingSeq(createPaintingSeqCopy(ind))
        .paintingSeqRandomKey(new ArrayList<>(ind.getPaintingSeqRandomKey()))
        .slicingOrderRandomKey(new ArrayList<>(ind.getSlicingOrderRandomKey()))
        .orientationProb(createOrientationProbCopy(ind))
        .orientations(new ArrayList<>(ind.getOrientations())) // TODO check if deep copy is required
        .orientationsResolved(new ArrayList<>(ind.getOrientationsResolved()))
        .objectiveValue(ind.getObjectiveValue())
        .build();
  }

  private List<Painting> createPaintingSeqCopy(Individual ind) {
    return ind.getPaintingSeq().stream()
        .map(paintingCopyFactory::createCopy)
        .collect(Collectors.toList());
  }

  private List<OrientationProbability> createOrientationProbCopy(Individual ind) {
    return ind.getOrientationProb().stream()
        .map(orientationProbabilityCopyFactory::createCopy)
        .collect(Collectors.toList());
  }
}
