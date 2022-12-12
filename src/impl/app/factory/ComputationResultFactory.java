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
  public ComputationResultDto create(ComputationContext context) {
    return ComputationResultDto.builder()
        .applicationVersion(applicationVersion)
        .durationMillis(context.getComputationResult().getDurationMillis())
        .gaResult(gaResultFactory.create(context))
        .gaParameters(context.getCreateComputationDto().getGaParams())
        .instanceParameters(context.getCreateComputationDto().getInstanceParameters())
        .build();
  }
}
