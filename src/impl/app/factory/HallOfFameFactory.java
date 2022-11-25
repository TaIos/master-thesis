package factory;

import logic.genetic.HallOfFame;
import models.dto.CreateComputationDto;

import javax.inject.Singleton;

@Singleton
public class HallOfFameFactory implements Factory<CreateComputationDto, HallOfFame> {
  @Override
  public HallOfFame create(CreateComputationDto dto) {
    return new HallOfFame(dto.getGaParams().getMaxNumberOfIter());
  }
}
