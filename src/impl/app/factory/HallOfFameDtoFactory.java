package factory;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.HallOfFame;
import models.dto.HallOfFameDto;
import models.dto.HallOfFameRecordDto;
import models.entity.GAResult;
import models.entity.HallOfFameRecord;

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
                    .bestIndividual(
                        individualDtoFactory.create(r.getBestIndividual(), r.getIteration()))
                    .build())
        .collect(Collectors.toList());
  }
}
