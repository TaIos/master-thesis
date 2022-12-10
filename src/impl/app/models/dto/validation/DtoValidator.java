package models.dto.validation;

import com.fasterxml.jackson.databind.JsonNode;
import exceptions.DtoConstraintViolationException;
import java.util.List;
import java.util.Map;
import javax.inject.Singleton;
import models.dto.Dto;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.context.OValContext;
import net.sf.oval.internal.MessageRenderer;
import net.sf.oval.internal.util.StringUtils;
import net.sf.oval.localization.context.DefaultOValContextRenderer;
import net.sf.oval.localization.context.OValContextRenderer;
import net.sf.oval.localization.value.MessageValueFormatter;
import net.sf.oval.localization.value.ToStringMessageValueFormatter;
import play.libs.Json;
import play.mvc.Http;

@Singleton
public class DtoValidator extends Validator {

  private static final OValContextRenderer contextRenderer = DefaultOValContextRenderer.INSTANCE;
  private static final MessageValueFormatter messageValueFormatter = ToStringMessageValueFormatter.INSTANCE;

  @Override
  protected String renderMessage(
      List<OValContext> contextPath,
      Object invalidValue,
      String messageKey,
      Map<String, ?> messageValues) {

    String message = MessageRenderer.renderMessage(messageKey, messageValues);

    // if there are no place holders in the message simply return it
    if (message.indexOf('{') == -1) {
      return message;
    }

    String renderedCtx = contextRenderer.render(contextPath);
    String editedCtx = org.apache.commons.lang3.StringUtils.substringAfterLast(renderedCtx, ".");

    message = StringUtils.replaceAll(message, "{context}", editedCtx);
    message = StringUtils.replaceAll(message, "{invalidValue}",
        messageValueFormatter.format(invalidValue));

    return message;
  }

  public <T extends Dto> T deserializeAndValidate(Http.Request req, Class<T> classToDeserialize)
      throws DtoConstraintViolationException {
    return deserializeAndValidate(req.body().asJson(), classToDeserialize);
  }

  public <T extends Dto> T deserializeAndValidate(JsonNode body, Class<T> classToDeserialize)
      throws DtoConstraintViolationException {
    T dto = Json.fromJson(body, classToDeserialize);
    List<ConstraintViolation> violations = validate(dto);
    if (violations.size() > 0) {
      throw new DtoConstraintViolationException(violations);
    }
    return dto;
  }
}
