package factory;

import exceptions.DtoConstraintViolationException;
import javax.inject.Singleton;
import models.dto.GAParametersDto;
import models.dto.InitialPopulationDivisionCountsDto;
import models.dto.PopulationDivisionCountsDto;
import models.entity.PopulationDivisionCounts;

@Singleton
public class PopulationDivisionCountsFactory implements
    Factory<GAParametersDto, PopulationDivisionCounts> {

  @Override
  public PopulationDivisionCounts create(GAParametersDto dto)
      throws DtoConstraintViolationException {
    throwIfNotSumToOne(dto.getInitialPopulationDivisionCounts());
    int popSize = dto.getPopulationSize();
    PopulationDivisionCountsDto counts = dto.getPopulationDivisionCounts();

    int eliteCnt = (int) (counts.getElite() * popSize);
    int averageCnt = (int) (counts.getAverage() * popSize);
    int worstCnt = popSize - eliteCnt - averageCnt;
    int childrenCnt = (int) (counts.getChildren() * popSize);
    int mutantCnt = (int) (counts.getMutant() * popSize);
    int winnerCnt = (int) (counts.getWinner() * popSize);
    int randomCnt = popSize - eliteCnt - childrenCnt - mutantCnt - winnerCnt;

    assert (eliteCnt + averageCnt + worstCnt == dto.getPopulationSize());
    assert (eliteCnt + childrenCnt + mutantCnt + winnerCnt + randomCnt == dto.getPopulationSize());

    return PopulationDivisionCounts.builder()
        .elite(eliteCnt)
        .average(averageCnt)
        .worst(worstCnt)
        .children(childrenCnt)
        .mutant(mutantCnt)
        .winner(winnerCnt)
        .random(randomCnt)
        .build();
  }

  private void throwIfNotSumToOne(InitialPopulationDivisionCountsDto dto)
      throws DtoConstraintViolationException {
    final double DELTA = 0.0001;
    if (Math.abs(1 - (dto.getRandom() + dto.getGreedy())) > DELTA) {
      throw new DtoConstraintViolationException(
          "Random and greedy percentages must sum up to one.");
    }

  }


}
