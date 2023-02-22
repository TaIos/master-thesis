package factory;

import java.util.ArrayList;
import models.dto.GAParametersDto;
import models.entity.NormalizedProbabilityVectorSumCrossoverParameters;

public class NormalizedProbabilityVectorSumCrossoverParametersFactory implements
    Factory<GAParametersDto, NormalizedProbabilityVectorSumCrossoverParameters> {

  @Override
  public NormalizedProbabilityVectorSumCrossoverParameters create(GAParametersDto dto) {
    return new NormalizedProbabilityVectorSumCrossoverParameters(
        new ArrayList<>(dto.getOrientationWeights()));
  }

}
