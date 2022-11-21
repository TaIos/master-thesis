package exceptions;

import models.dto.Dto;
import net.sf.oval.ConstraintViolation;

import java.util.List;
import java.util.stream.Collectors;

public class DtoConstraintViolationException extends BaseException {

  public <T extends Dto> DtoConstraintViolationException(
      List<ConstraintViolation> violations, Class<T> validatedClass) {
    super(
        String.format(
            "Violations: %s. Validated class: %s",
            String.join(
                ", ",
                prefixWithOrdinalNumber(
                    violations.stream().map(Object::toString).collect(Collectors.toList()))),
            validatedClass.getName()));
  }

  private static List<String> prefixWithOrdinalNumber(List<String> list) {
    for (int i = 0; i < list.size(); i++) {
      list.set(i, i + 1 + ") " + list.get(i));
    }
    return list;
  }
}
