package factory;

import models.dto.ComputationResultDto;
import models.entity.ComputationContext;

import javax.inject.Inject;

public class ComputationResultFactory implements Factory<ComputationContext, ComputationResultDto> {

  private final GaResultFactory gaResultFactory;

  @Inject
  public ComputationResultFactory(GaResultFactory gaResultFactory) {
    this.gaResultFactory = gaResultFactory;
  }

  @Override
  public ComputationResultDto create(ComputationContext context) {
    return ComputationResultDto.builder()
        .durationMillis(context.getComputationResult().getDurationMillis())
        .gaResult(gaResultFactory.create(context))
        .gaParameters(context.getCreateComputationDto().getGaParams())
        .instanceParameters(context.getCreateComputationDto().getInstanceParams())
        .build();
  }
}
