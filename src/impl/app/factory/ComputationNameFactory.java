package factory;

import models.dto.CreateComputationDto;
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
    return String.format(
        "%s_%s", dto.getGaParams().getGeneticAlgorithm(), randomStringGenerator.generate());
  }
}
