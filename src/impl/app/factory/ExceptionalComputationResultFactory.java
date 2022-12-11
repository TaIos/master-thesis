package factory;

import models.dto.ExceptionalComputationResultDto;
import models.entity.ComputationContext;

public class ExceptionalComputationResultFactory
    implements Factory<ComputationContext, ExceptionalComputationResultDto> {

  @Override
  public ExceptionalComputationResultDto create(ComputationContext context) {
    return create(context, "");
  }

  public ExceptionalComputationResultDto create(ComputationContext context, Throwable e) {
    return create(context, e.toString());
  }

  public ExceptionalComputationResultDto create(ComputationContext context, String e) {
    return ExceptionalComputationResultDto.builder()
        .exception(e)
        .gaParameters(context.getCreateComputationDto().getGaParams())
        .instanceParameters(context.getCreateComputationDto().getInstanceParameters())
        .build();
  }
}
