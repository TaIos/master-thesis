package factory;

import factory.provider.RandomKeyDecoderProvider;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.resolvers.RandomKeyDecoder;
import models.dto.IndividualDto;
import models.entity.EvaluatedIndividual;
import models.entity.EvaluatedSlicingLayout;
import models.entity.GAResult;
import models.entity.HallOfFameRecord;
import models.entity.Orientation;
import models.entity.Orientation.OrientationType;
import models.entity.OrientationProbability;
import models.entity.Painting;
import models.entity.PaintingPlacement;
import models.entity.Rectangle;

@Singleton
public class IndividualDtoFactory implements Factory<GAResult, IndividualDto> {

  private final RandomKeyDecoder decoder;

  @Inject
  public IndividualDtoFactory(RandomKeyDecoderProvider decoderProvider) {
    decoder = decoderProvider.get();
  }

  @Override
  public IndividualDto create(GAResult gaResult) {
    HallOfFameRecord bestRecord = gaResult.getHallOfFame().getBestRecord();
    return create(bestRecord.getBestIndividual(), bestRecord.getIteration());
  }

  public IndividualDto create(EvaluatedIndividual ind, int iteration) {
    IndividualDto dto = create(ind);
    dto.setIteration(iteration);
    return dto;
  }

  public IndividualDto create(EvaluatedSlicingLayout layout) {
    return IndividualDto.builder()
        .paintingAllocatedSpace(createPaintingAllocatedSpace(layout.getPlacements()))
        .paintingPlacement(createPaintingPlacement(layout.getPlacements()))
        .objectiveValue(layout.getObjectiveValue())
        .build();
  }


  public IndividualDto create(EvaluatedIndividual ind) {
    return IndividualDto.builder()
        .objectiveValue(ind.getLayout().getObjectiveValue())
        .paintingSeq(createPaintingSequence(ind.getIndividual().getPaintingSeq()))
        .paintingSeqDecoded(createPaintingSequence(
            decoder.decode(ind.getIndividual().getPaintingSeq(), ind.getIndividual()
                .getPaintingSeqRandomKey())))
        .paintingSeqRandomKey(ind.getIndividual().getPaintingSeqRandomKey())
        .slicingOrderDecoded(decoder.decode(ind.getIndividual().getSlicingOrderRandomKey()))
        .slicingOrderRandomKey(ind.getIndividual().getSlicingOrderRandomKey())
        .orientations(createOrientations(ind.getIndividual().getOrientationProb()))
        .orientationsCapped(createOrientationsCapped(ind.getOrientationsCapped()))
        .orientationProbabilities(createOrientationProbabilities(ind.getIndividual()
            .getOrientationProb()))
        .paintingAllocatedSpace(createPaintingAllocatedSpace(ind.getLayout().getPlacements()))
        .paintingPlacement(createPaintingPlacement(ind.getLayout().getPlacements()))
        .build();
  }


  private List<String> createPaintingSequence(List<Painting> paintingSequence) {
    return paintingSequence.stream().map(Painting::getIdent).collect(Collectors.toList());
  }

  private List<String> createOrientations(List<OrientationProbability> orientations) {
    return orientations.stream()
        .map(OrientationProbability::getMostProbable)
        .map(OrientationType::getLabel)
        .collect(Collectors.toList());
  }

  private List<String> createOrientationsCapped(List<Orientation> orientations) {
    return orientations.stream()
        .map(Orientation::getType)
        .map(OrientationType::getLabel)
        .collect(Collectors.toList());
  }

  private List<List<Double>> createOrientationProbabilities(
      List<OrientationProbability> orientationProb) {
    return orientationProb.stream()
        .map(OrientationProbability::getProbabilityVector)
        .collect(Collectors.toList());
  }


  private List<String> createPaintingAllocatedSpace(List<PaintingPlacement> placements) {
    return serializeRectangleList(
        placements.stream().map(PaintingPlacement::getAllocatedSpace).collect(
            Collectors.toList()));
  }

  private List<String> createPaintingPlacement(List<PaintingPlacement> placements) {
    return serializeRectangleList(
        placements.stream().map(PaintingPlacement::getPlacement).collect(
            Collectors.toList()));
  }


  private List<String> serializeRectangleList(List<Rectangle> recs) {
    return recs.stream()
        .map(Rectangle::toString)
        .collect(Collectors.toList());
  }

}
