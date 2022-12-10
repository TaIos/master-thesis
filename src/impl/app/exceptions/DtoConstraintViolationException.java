package exceptions;

import java.util.List;
import java.util.stream.Collectors;
import models.dto.Dto;
import net.sf.oval.ConstraintViolation;

public class DtoConstraintViolationException extends BaseException {

  public <T extends Dto> DtoConstraintViolationException(List<ConstraintViolation> violations) {
    super(
        String.format(
            "Violations: %s",
            String.join(
                ", ",
                prefixWithOrdinalNumber(
                    violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.toList())))));
  }

  private static List<String> prefixWithOrdinalNumber(List<String> list) {
    for (int i = 0; i < list.size(); i++) {
      list.set(i, i + 1 + ") " + list.get(i));
    }
    return list;
  }
}
