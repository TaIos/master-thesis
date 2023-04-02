package factory;

import factory.provider.IndividualCopyFactoryProvider;
import factory.provider.ObjectiveValueComparatorProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.HallOfFame;
import logic.genetic.resolvers.MaximumWildCardCountResolver;
import models.dto.CreateComputationDto;

@Singleton
public class HallOfFameFactory implements Factory<CreateComputationDto, HallOfFame> {

  private final IndividualCopyFactoryProvider individualCopyFactoryProvider;
  private final ObjectiveValueComparatorProvider objectiveValueComparatorProvider;
  private final MaximumWildCardCountResolver maximumWildCardCountResolver;

  @Inject
  public HallOfFameFactory(IndividualCopyFactoryProvider individualCopyFactoryProvider,
      ObjectiveValueComparatorProvider objectiveValueComparatorProvider,
      MaximumWildCardCountResolver maximumWildCardCountResolver) {
    this.individualCopyFactoryProvider = individualCopyFactoryProvider;
    this.objectiveValueComparatorProvider = objectiveValueComparatorProvider;
    this.maximumWildCardCountResolver = maximumWildCardCountResolver;
  }

  @Override
  public HallOfFame create(CreateComputationDto dto) {
    return new HallOfFame(
        dto.getGaParameters().getMaxNumberOfIter(),
        individualCopyFactoryProvider.get(),
        objectiveValueComparatorProvider.get(),
        maximumWildCardCountResolver);
  }
}
