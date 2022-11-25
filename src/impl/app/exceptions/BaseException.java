package exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Http;

public abstract class BaseException extends Exception {

  private final String message;

  BaseException(String message) {
    super(message);
    this.message = message;
  }

  public int getHttpStatus() {
    return Http.Status.BAD_REQUEST;
  }
  public JsonNode toJson() {
    return Json.newObject().putObject("error").put("message", message);
  }
}
