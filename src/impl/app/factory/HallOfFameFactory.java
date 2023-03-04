package factory;

import factory.provider.IndividualCopyFactoryProvider;
import factory.provider.ObjectiveValueComparatorProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.HallOfFame;
import models.dto.CreateComputationDto;

@Singleton
public class HallOfFameFactory implements Factory<CreateComputationDto, HallOfFame> {

  private final IndividualCopyFactoryProvider individualCopyFactoryProvider;
  private final ObjectiveValueComparatorProvider objectiveValueComparatorProvider;

  @Inject
  public HallOfFameFactory(IndividualCopyFactoryProvider individualCopyFactoryProvider,
      ObjectiveValueComparatorProvider objectiveValueComparatorProvider) {
    this.individualCopyFactoryProvider = individualCopyFactoryProvider;
    this.objectiveValueComparatorProvider = objectiveValueComparatorProvider;
  }

  @Override
  public HallOfFame create(CreateComputationDto dto) {
    return new HallOfFame(dto.getGaParameters().getMaxNumberOfIter(),
        individualCopyFactoryProvider.get(), objectiveValueComparatorProvider.get());
  }
}
