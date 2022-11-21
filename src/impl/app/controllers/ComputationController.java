package controllers;

import exceptions.BaseException;
import models.dto.CreateComputationDto;
import models.dto.validation.DtoValidator;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ComputationService;

import javax.inject.Inject;

import static play.libs.Json.toJson;

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
}
