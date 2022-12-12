package factory;

import factory.provider.RandomKeyDecoderProvider;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.RandomKeyDecoder;
import models.dto.IndividualDto;
import models.entity.BestIndividual;
import models.entity.Individual;
import models.entity.Orientation;
import models.entity.Painting;
import models.entity.Rectangle;

@Singleton
public class IndividualDtoFactory implements Factory<Individual, IndividualDto> {

  private final RandomKeyDecoder decoder;
  private final RectangleFactory rectangleFactory;

  @Inject
  public IndividualDtoFactory(RandomKeyDecoderProvider decoderProvider,
      RectangleFactory rectangleFactory) {
    this.decoder = decoderProvider.get();
    this.rectangleFactory = rectangleFactory;
  }

  @Override
  public IndividualDto create(Individual ind) {
    return IndividualDto.builder()
        .paintingSeq(createPaintingSequence(ind.getPaintingSeq()))
        .paintingSeqDecoded(createPaintingSequence(decoder.decodePaintingSequence(ind)))
        .paintingSeqRandomKey(ind.getPaintingSeqRandomKey())
        .slicingOrderDecoded(decoder.decodeSlicingOrder(ind))
        .slicingOrderRandomKey(ind.getSlicingOrderRandomKey())
        .orientations(createOrientations(ind.getOrientations()))
        .orientationsResolved(createOrientations(ind.getOrientationsResolved()))
        .objectiveValue(ind.getObjectiveValue())
        .paintingAllocatedSpace(createPaintingAllocatedSpace(ind.getPaintingSeq()))
        .paintingPlacement(createPaintingPlacement(ind.getPaintingSeq()))
        .build();
  }

  public IndividualDto create(BestIndividual bestIndividual) {
    return create(bestIndividual.getBestIndividual(), bestIndividual.getIteration());
  }

  public IndividualDto create(Individual ind, int iteration) {
    IndividualDto dto = create(ind);
    dto.setIteration(iteration);
    return dto;
  }

  private List<String> createPaintingSequence(List<Painting> paintingSequence) {
    return paintingSequence.stream().map(Painting::getIdent).collect(Collectors.toList());
  }

  private List<String> createOrientations(List<Orientation> orientations) {
    return orientations.stream()
        .map(Orientation::getType)
        .map(Orientation.Type::getLabel)
        .collect(Collectors.toList());
  }

  private List<String> createPaintingAllocatedSpace(List<Painting> paintingSequence) {
    return paintingSequence.stream()
        .map(Painting::getAllocatedSpace)
        .map(Rectangle::toString)
        .collect(Collectors.toList());
  }

  private List<String> createPaintingPlacement(List<Painting> paintingSeq) {
    return paintingSeq.stream()
        .map(p -> rectangleFactory.create(
            p.getPlacement().getX(),
            p.getPlacement().getY(),
            p.getWidth(),
            p.getHeight())
        ).map(Rectangle::toString).collect(Collectors.toList());
  }

}
