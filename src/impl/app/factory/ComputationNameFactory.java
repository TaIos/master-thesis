package factory;

import models.dto.CreateComputationDto;
import models.dto.CreateComputationFromDatasetDto;
import models.dto.DatasetDto;
import utils.RandomStringGenerator;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ComputationNameFactory implements Factory<CreateComputationDto, String> {

  private final RandomStringGenerator randomStringGenerator;

  @Inject
  public ComputationNameFactory(RandomStringGenerator randomStringGenerator) {
    this.randomStringGenerator = randomStringGenerator;
  }

  @Override
  public String create(CreateComputationDto dto) {
    return create(dto.getGaParams().getGeneticAlgorithm());
  }

  public String create(CreateComputationFromDatasetDto dto, DatasetDto datasetDto) {
    return create(
        String.format("%s_%s", dto.getGaParams().getGeneticAlgorithm(), datasetDto.getName()));
  }

  private String create(String prefix) {
    return String.format("%s_%s", prefix, randomStringGenerator.generate());
  }
}
