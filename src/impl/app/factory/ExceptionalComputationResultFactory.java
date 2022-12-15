package factory;

import factory.provider.ApplicationVersionProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.dto.ExceptionalComputationResultDto;
import models.entity.ComputationContext;

@Singleton
public class ExceptionalComputationResultFactory
    implements Factory<ComputationContext, ExceptionalComputationResultDto> {

  private final String applicationVersion;

  @Inject
  public ExceptionalComputationResultFactory(
      ApplicationVersionProvider applicationVersionProvider) {
    this.applicationVersion = applicationVersionProvider.get();
  }

  @Override
  public ExceptionalComputationResultDto create(ComputationContext context) {
    return create(context, "");
  }

  public ExceptionalComputationResultDto create(ComputationContext context, Throwable e) {
    return create(context, e.toString());
  }

  public ExceptionalComputationResultDto create(ComputationContext context, String e) {
    return ExceptionalComputationResultDto.builder()
        .applicationVersion(applicationVersion)
        .exception(e)
        .gaParameters(context.getCreateComputationDto().getGaParameters())
        .instanceParameters(context.getCreateComputationDto().getInstanceParameters())
        .build();
  }
}
