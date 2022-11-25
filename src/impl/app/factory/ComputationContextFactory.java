package factory;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import models.dto.CreateComputationDto;
import models.entity.ComputationContext;
import org.slf4j.Logger;
import services.ResultWriterService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ComputationContextFactory
    implements Factory<CreateComputationDto, ComputationContext> {

  private final GeneticAlgorithmFactory geneticAlgorithmFactory;
  private final ComputationNameFactory computationNameFactory;
  private final CustomLoggerFactory loggerFactory;
  private final ResultWriterService resultWriter;

  @Inject
  public ComputationContextFactory(
      GeneticAlgorithmFactory geneticAlgorithmFactory,
      ComputationNameFactory computationNameFactory,
      CustomLoggerFactory loggerFactory,
      ResultWriterService resultWriter) {
    this.geneticAlgorithmFactory = geneticAlgorithmFactory;
    this.computationNameFactory = computationNameFactory;
    this.loggerFactory = loggerFactory;
    this.resultWriter = resultWriter;
  }

  @Override
  public ComputationContext create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    String name = computationNameFactory.create(dto);
    Logger logger = loggerFactory.create(name);
    return ComputationContext.builder()
        .id(name)
        .createComputationDto(dto)
        .geneticAlgorithm(geneticAlgorithmFactory.create(dto, logger))
        .computationResult(null)
        .resultDir(resultWriter.createDirForComputationOutput(name))
        .logger(logger)
        .build();
  }
}
