package factory;

import models.dto.GAResultDto;
import models.entity.ComputationContext;
import models.entity.GAResult;

public class GaResultFactory implements Factory<GAResult, GAResultDto> {
  @Override
  public GAResultDto create(GAResult gaResult) {
    // TODO
    return GAResultDto.builder().test(gaResult.getTest()).build();
  }

  public GAResultDto create(ComputationContext context) {
    return create(context.getComputationResult().getGaResult());
  }
}
