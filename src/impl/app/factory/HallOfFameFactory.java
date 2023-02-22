package factory;

import factory.provider.IndividualCopyFactoryProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.HallOfFame;
import models.dto.CreateComputationDto;

@Singleton
public class HallOfFameFactory implements Factory<CreateComputationDto, HallOfFame> {

  private final IndividualCopyFactoryProvider individualCopyFactoryProvider;

  @Inject
  public HallOfFameFactory(IndividualCopyFactoryProvider individualCopyFactoryProvider) {
    this.individualCopyFactoryProvider = individualCopyFactoryProvider;
  }

  @Override
  public HallOfFame create(CreateComputationDto dto) {
    return new HallOfFame(dto.getGaParameters().getMaxNumberOfIter(),
        individualCopyFactoryProvider.get());
  }
}
