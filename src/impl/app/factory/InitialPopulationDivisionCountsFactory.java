package factory;

import exceptions.DtoConstraintViolationException;
import javax.inject.Singleton;
import models.dto.GAParametersDto;
import models.dto.PopulationDivisionCountsDto;
import models.entity.InitialPopulationDivisionCounts;


@Singleton
public class InitialPopulationDivisionCountsFactory implements
    Factory<GAParametersDto, InitialPopulationDivisionCounts> {


  @Override
  public InitialPopulationDivisionCounts create(GAParametersDto dto)
      throws DtoConstraintViolationException {
    throwIfNotSumToOne(dto.getPopulationDivisionCounts());
    int randomCnt = (int) (dto.getPopulationSize() * dto.getInitialPopulationDivisionCounts()
        .getRandom());
    int greedyCnt = dto.getPopulationSize() - randomCnt;
    assert (randomCnt + greedyCnt == dto.getPopulationSize());

    return InitialPopulationDivisionCounts.builder()
        .random(randomCnt)
        .greedy(greedyCnt)
        .build();
  }

  private void throwIfNotSumToOne(PopulationDivisionCountsDto dto)
      throws DtoConstraintViolationException {
    final double DELTA = 0.0001;
    if (Math.abs(1 - (dto.getElite() + dto.getAverage() + dto.getWorst())) > DELTA) {
      throw new DtoConstraintViolationException(
          "Elite, average and worst percentages must sum up to one.");
    }

    if (Math.abs(1 - (dto.getElite() + dto.getChildren() + dto.getMutant() + dto.getWinner()
        + dto.getRandom())) > DELTA) {
      throw new DtoConstraintViolationException(
          "Elite, children, mutant, winner and random percentages must sum up to one.");
    }
  }
}
