package factory;

import models.dto.ComputationResultPromiseDto;
import models.entity.ComputationContext;

public class ComputationResultPromiseFactory
    implements Factory<ComputationContext, ComputationResultPromiseDto> {
  @Override
  public ComputationResultPromiseDto create(ComputationContext context) {
    return ComputationResultPromiseDto.builder()
        .id(context.getId())
        .outputDirectory(context.getResultDir().getAbsolutePath())
        .build();
  }
}
