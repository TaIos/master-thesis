package factory;

import exceptions.DtoConstraintViolationException;
import exceptions.DtoConstraintViolationExceptionWrapper;
import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import models.dto.CreateComputationDto;
import models.dto.CreateComputationFromDatasetDto;
import models.dto.DatasetDto;
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
  private final DatasetFactory datasetFactory;
  private final CustomLoggerFactory loggerFactory;
  private final ResultWriterService resultWriter;

  @Inject
  public ComputationContextFactory(
      GeneticAlgorithmFactory geneticAlgorithmFactory,
      ComputationNameFactory computationNameFactory,
      DatasetFactory datasetFactory,
      CustomLoggerFactory loggerFactory,
      ResultWriterService resultWriter) {
    this.geneticAlgorithmFactory = geneticAlgorithmFactory;
    this.computationNameFactory = computationNameFactory;
    this.datasetFactory = datasetFactory;
    this.loggerFactory = loggerFactory;
    this.resultWriter = resultWriter;
  }

  @Override
  public ComputationContext create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return create(dto, computationNameFactory.create(dto));
  }

  public ComputationContext create(CreateComputationDto dto, String computationIdent)
      throws EntityNotFoundException, ImplementationNotFoundException {
    Logger logger = loggerFactory.create(computationIdent);
    return ComputationContext.builder()
        .id(computationIdent)
        .createComputationDto(dto)
        .geneticAlgorithm(geneticAlgorithmFactory.create(dto, logger))
        .computationResult(null)
        .resultDir(resultWriter.createDirForComputationOutput(computationIdent))
        .logger(logger)
        .build();
  }

  public ComputationContext create(CreateComputationFromDatasetDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException,
          DtoConstraintViolationException, DtoConstraintViolationExceptionWrapper {
    DatasetDto datasetDto = datasetFactory.create(dto);
    return create(
        CreateComputationDto.builder()
            .instanceParams(datasetDto.getData())
            .gaParams(dto.getGaParams())
            .build(),
        computationNameFactory.create(dto, datasetDto));
  }
}
