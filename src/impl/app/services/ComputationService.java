package services;

import exceptions.DtoConstraintViolationException;
import exceptions.DtoConstraintViolationExceptionWrapper;
import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import factory.ComputationContextFactory;
import factory.ComputationResultPromiseFactory;
import factory.GATimeMeasureWrapperFactory;
import javax.inject.Inject;
import models.dto.ComputationResultPromiseDto;
import models.dto.CreateComputationDto;
import models.dto.CreateComputationFromDatasetDto;
import models.entity.ComputationContext;

public class ComputationService {

  private final factory.GATimeMeasureWrapperFactory gaTimeMeasureWrapperFactory;
  private final ComputationContextFactory computationContextFactory;
  private final ComputationResultPromiseFactory computationResultPromiseFactory;

  private final ResultWriterService resultWriterService;

  @Inject
  public ComputationService(
      GATimeMeasureWrapperFactory gaTimeMeasureWrapperFactory,
      ComputationContextFactory computationContextFactory,
      ComputationResultPromiseFactory computationResultPromiseFactory,
      ResultWriterService resultWriterService) {
    this.gaTimeMeasureWrapperFactory = gaTimeMeasureWrapperFactory;
    this.computationContextFactory = computationContextFactory;
    this.computationResultPromiseFactory = computationResultPromiseFactory;
    this.resultWriterService = resultWriterService;
  }

  public ComputationResultPromiseDto compute(CreateComputationFromDatasetDto dto)
      throws DtoConstraintViolationException, EntityNotFoundException,
      ImplementationNotFoundException, DtoConstraintViolationExceptionWrapper, FunctionNotValidException {
    return compute(computationContextFactory.create(dto));
  }

  public ComputationResultPromiseDto compute(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException, FunctionNotValidException {
    return compute(computationContextFactory.create(dto));
  }

  public ComputationResultPromiseDto compute(ComputationContext context) {
    gaTimeMeasureWrapperFactory
        .createAsCompletableFuture(context)
        .thenAccept(resultWriterService::writeComputationResult)
        .exceptionally(e -> writeAndLogExc(context, e));
    return computationResultPromiseFactory.create(context);
  }

  public Void writeAndLogExc(ComputationContext context, Throwable e) {
    context.getLogger().error("Error occurred during computation [{}]", e.toString());
    resultWriterService.writeExceptionalResult(context, e);
    return null;
  }
}
