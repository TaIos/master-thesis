package factory;

import logic.genetic.HallOfFame;
import logic.genetic.HallOfFame.HallOfFameRecord;
import models.dto.HallOfFameDto;
import models.dto.HallOfFameRecordDto;
import models.entity.GAResult;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class HallOfFameDtoFactory implements Factory<HallOfFame, HallOfFameDto> {

  private final IndividualDtoFactory individualDtoFactory;

  @Inject
  public HallOfFameDtoFactory(IndividualDtoFactory individualDtoFactory) {
    this.individualDtoFactory = individualDtoFactory;
  }

  @Override
  public HallOfFameDto create(HallOfFame hallOfFame) {
    return HallOfFameDto.builder().records(create(hallOfFame.getRecords())).build();
  }

  public HallOfFameDto create(GAResult gaResult) {
    return create(gaResult.getHallOfFame());
  }

  private List<HallOfFameRecordDto> create(List<HallOfFameRecord> records) {
    return records.stream()
        .map(
            r ->
                HallOfFameRecordDto.builder()
                    .iteration(r.getIteration())
                    .objectiveMin(r.getObjectiveMin())
                    .objectiveMax(r.getObjectiveMax())
                    .objectiveAvg(r.getObjectiveAvg())
                    .bestIndividual(individualDtoFactory.create(r.getBestIndividual()))
                    .build())
        .collect(Collectors.toList());
  }
}
