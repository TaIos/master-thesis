package factory;

import factory.provider.RandomKeyDecoderProvider;
import logic.genetic.RandomKeyDecoder;
import models.dto.IndividualDto;
import models.entity.BestIndividual;
import models.entity.Facility;
import models.entity.Individual;
import models.entity.Orientation;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class IndividualDtoFactory implements Factory<Individual, IndividualDto> {

  private final RandomKeyDecoder decoder;

  @Inject
  public IndividualDtoFactory(RandomKeyDecoderProvider decoderProvider) {
    this.decoder = decoderProvider.get();
  }

  @Override
  public IndividualDto create(Individual ind) {
    return IndividualDto.builder()
        .facilitySequence(createFacilitySequence(ind.getFacilitySequence()))
        .facilitySequenceDecoded(createFacilitySequence(decoder.decodeFacilitySequence(ind)))
        .facilitySequenceRandomKey(ind.getFacilitySequenceRandomKey())
        .slicingOrderDecoded(decoder.decodeSlicingOrder(ind))
        .slicingOrderRandomKey(ind.getSlicingOrderRandomKey())
        .orientations(createOrientations(ind.getOrientations()))
        .objectiveValue(ind.getObjectiveValue())
        .facilityPlacement(createFacilityPlacement(ind.getFacilitySequence()))
        .build();
  }

  public IndividualDto create(BestIndividual bestIndividual) {
    IndividualDto dto = create(bestIndividual.getBestIndividual());
    dto.setIteration(bestIndividual.getIteration());
    return dto;
  }

  private List<String> createFacilitySequence(List<Facility> facilitySequence) {
    return facilitySequence.stream().map(Facility::getIdent).collect(Collectors.toList());
  }

  private List<String> createOrientations(List<Orientation> orientations) {
    return orientations.stream()
        .map(Orientation::getType)
        .map(Orientation.Type::getLabel)
        .collect(Collectors.toList());
  }

  private List<String> createFacilityPlacement(List<Facility> facilitySequence) {
    return facilitySequence.stream()
        .map(Facility::getPlacement)
        .map(Object::toString)
        .collect(Collectors.toList());
  }
}
