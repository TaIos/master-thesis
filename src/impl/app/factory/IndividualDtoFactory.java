package factory;

import models.dto.IndividualDto;
import models.entity.BestIndividual;
import models.entity.Facility;
import models.entity.Individual;
import models.entity.Orientation;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class IndividualDtoFactory implements Factory<Individual, IndividualDto> {

  @Override
  public IndividualDto create(Individual individual) {
    return IndividualDto.builder()
        .facilitySequence(createFacilitySequence(individual.getFacilitySequence()))
        .slicingOrder(new ArrayList<>(individual.getSlicingOrder()))
        .orientations(createOrientations(individual.getOrientations()))
        .objectiveValue(individual.getObjectiveValue())
        .facilityPlacement(createFacilityPlacement(individual.getFacilitySequence()))
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
