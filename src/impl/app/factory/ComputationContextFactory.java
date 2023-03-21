package factory;

import exceptions.BaseException;
import exceptions.DtoConstraintViolationException;
import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import exceptions.InvalidFieldValueInJsonException;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.dto.CreateComputationDto;
import models.dto.CreateComputationFromDatasetDto;
import models.dto.DatasetDto;
import models.entity.ComputationContext;
import org.slf4j.Logger;
import services.ResultWriterService;

@Singleton
public class ComputationContextFactory
    implements Factory<CreateComputationDto, ComputationContext> {

  private final ResultWriterService resultWriter;

  private final GeneticAlgorithmFactory geneticAlgorithmFactory;
  private final ComputationNameFactory computationNameFactory;
  private final DatasetFactory datasetFactory;
  private final CustomLoggerFactory loggerFactory;


  @Inject
  public ComputationContextFactory(ResultWriterService resultWriter,
      GeneticAlgorithmFactory geneticAlgorithmFactory,
      ComputationNameFactory computationNameFactory, DatasetFactory datasetFactory,
      CustomLoggerFactory loggerFactory) {
    this.resultWriter = resultWriter;
    this.geneticAlgorithmFactory = geneticAlgorithmFactory;
    this.computationNameFactory = computationNameFactory;
    this.datasetFactory = datasetFactory;
    this.loggerFactory = loggerFactory;
  }

  @Override
  public ComputationContext create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException, FunctionNotValidException, InvalidFieldValueInJsonException, DtoConstraintViolationException {
    return create(dto, computationNameFactory.create(dto));
  }

  public ComputationContext create(CreateComputationDto dto, String computationIdent)
      throws EntityNotFoundException, ImplementationNotFoundException, FunctionNotValidException, InvalidFieldValueInJsonException, DtoConstraintViolationException {
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

  public ComputationContext create(CreateComputationFromDatasetDto dto) throws BaseException {
    DatasetDto datasetDto = datasetFactory.create(dto);
    return create(
        CreateComputationDto.builder()
            .instanceParameters(datasetDto.getData())
            .gaParameters(dto.getGaParameters())
            .objectiveParameters(dto.getObjectiveParameters())
            .build(),
        computationNameFactory.create(dto, datasetDto));
  }
}
