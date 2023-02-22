package exceptions;

public class EntityNotFoundException extends BaseException {


  public EntityNotFoundException(Class<?> clazz, String name) {
    this(clazz, name, "");
  }

  public EntityNotFoundException(Class<?> clazz, String name, String explanation) {
    super(
        String.format(
            "Entity [%s] with identifier [%s] was not found. %s", clazz.getSimpleName(), name,
            explanation));
  }
}

