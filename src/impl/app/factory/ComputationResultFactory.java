package factory;

import factory.provider.ApplicationVersionProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.dto.ComputationResultDto;
import models.entity.ComputationContext;

@Singleton
public class ComputationResultFactory implements Factory<ComputationContext, ComputationResultDto> {

  private final GaResultFactory gaResultFactory;
  private final String applicationVersion;

  @Inject
  public ComputationResultFactory(GaResultFactory gaResultFactory,
      ApplicationVersionProvider applicationVersionProvider) {
    this.gaResultFactory = gaResultFactory;
    this.applicationVersion = applicationVersionProvider.get();
  }

  @Override
  public ComputationResultDto create(ComputationContext ctx) {
    return ComputationResultDto.builder()
        .applicationVersion(applicationVersion)
        .durationMillis(ctx.getComputationResult().getDurationMillis())
        .gaResult(gaResultFactory.create(ctx))
        .gaParameters(ctx.getCreateComputationDto().getGaParameters())
        .instanceParameters(ctx.getCreateComputationDto().getInstanceParameters())
        .objectiveParameters(ctx.getCreateComputationDto().getObjectiveParameters())
        .build();
  }
}
