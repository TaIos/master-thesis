package factory;

import javax.inject.Inject;
import models.dto.GAResultDto;
import models.entity.ComputationContext;
import models.entity.GAResult;

public class GaResultFactory implements Factory<GAResult, GAResultDto> {

  private final HallOfFameDtoFactory hallOfFameDtoFactory;
  private final IndividualDtoFactory individualDtoFactory;

  @Inject
  public GaResultFactory(
      HallOfFameDtoFactory hallOfFameDtoFactory, IndividualDtoFactory individualDtoFactory) {
    this.hallOfFameDtoFactory = hallOfFameDtoFactory;
    this.individualDtoFactory = individualDtoFactory;
  }

  @Override
  public GAResultDto create(GAResult gaResult) {
    return GAResultDto.builder()
        .bestIndividual(individualDtoFactory.create(
            gaResult.getHallOfFame().getBestRecord().getBestIndividual()))
        .hallOfFame(hallOfFameDtoFactory.create(gaResult))
        .build();
  }

  public GAResultDto create(ComputationContext context) {
    return create(context.getComputationResult().getGaResult());
  }
}
