package exceptions;

public class ImplementationNotFoundException extends BaseException {

  public ImplementationNotFoundException(Class<?> clazz, String name) {
    super(
        String.format(
            "Implementation for class [%s] with parameter [%s] was not found.",
            clazz.getSimpleName(), name));
  }
}
