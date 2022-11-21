package models.dto.validation;

import com.fasterxml.jackson.databind.JsonNode;
import exceptions.DtoConstraintViolationException;
import models.dto.Dto;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import play.libs.Json;
import play.mvc.Http;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class DtoValidator extends Validator {

  public <T extends Dto> T deserializeAndValidate(Http.Request req, Class<T> classToDeserialize)
      throws DtoConstraintViolationException {
    return deserializeAndValidate(req.body().asJson(), classToDeserialize);
  }

  public <T extends Dto> T deserializeAndValidate(JsonNode body, Class<T> classToDeserialize)
      throws DtoConstraintViolationException {
    T dto = Json.fromJson(body, classToDeserialize);
    List<ConstraintViolation> violations = validate(dto);
    if (violations.size() > 0) {
      throw new DtoConstraintViolationException(violations, classToDeserialize);
    }
    return dto;
  }
}
