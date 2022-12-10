package controllers;

import static play.libs.Json.toJson;

import exceptions.BaseException;
import javax.inject.Inject;
import models.dto.CreateComputationDto;
import models.dto.CreateComputationFromDatasetDto;
import models.dto.validation.DtoValidator;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ComputationService;

public class ComputationController extends Controller {

  private final DtoValidator validator;
  private final ComputationService computationService;

  @Inject
  public ComputationController(DtoValidator validator, ComputationService computationService) {
    this.validator = validator;
    this.computationService = computationService;
  }

  public Result compute(Http.Request req) throws BaseException {
    CreateComputationDto dto = validator.deserializeAndValidate(req, CreateComputationDto.class);
    return ok(toJson(computationService.compute(dto)));
  }

  public Result computeForDataset(Http.Request req) throws BaseException {
    CreateComputationFromDatasetDto dto = validator.deserializeAndValidate(req,
        CreateComputationFromDatasetDto.class);
    return ok(toJson(computationService.compute(dto)));
  }


}
