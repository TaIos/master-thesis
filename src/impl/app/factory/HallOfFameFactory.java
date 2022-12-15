package factory;

import javax.inject.Singleton;
import logic.genetic.HallOfFame;
import models.dto.CreateComputationDto;

@Singleton
public class HallOfFameFactory implements Factory<CreateComputationDto, HallOfFame> {

  @Override
  public HallOfFame create(CreateComputationDto dto) {
    return new HallOfFame(dto.getGaParameters().getMaxNumberOfIter());
  }
}
