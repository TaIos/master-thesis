package exceptions;

public class EntityNotFoundException extends BaseException {

  public EntityNotFoundException(Class<?> clazz, String name) {
    super(
        String.format(
            "Entity [%s] with identifier [%s] was not found.", clazz.getSimpleName(), name));
  }
}
