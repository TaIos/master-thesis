package exceptions;

import java.util.Arrays;
import java.util.stream.Collectors;
import utils.EnumTypeInterface;

public class EntityNotFoundException extends BaseException {


  public EntityNotFoundException(Class<?> clazz, String name) {
    this(clazz, name, "");
  }

  public EntityNotFoundException(Class<?> clazz, String name, EnumTypeInterface[] available) {
    this(clazz, name,
        "Available: [" + Arrays.stream(available).map(EnumTypeInterface::getLabel).collect(
            Collectors.joining("],[")) + "]");
  }

  public EntityNotFoundException(Class<?> clazz, String name, String explanation) {
    super(
        String.format(
            "Entity [%s] with identifier [%s] was not found. %s", clazz.getSimpleName(), name,
            explanation));
  }
}

