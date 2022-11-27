package exceptions;

public class DtoConstraintViolationExceptionWrapper extends BaseException {

  public DtoConstraintViolationExceptionWrapper(DtoConstraintViolationException e, String msg) {
    super(String.format("%s. %s", msg, e.getMessage()));
  }
}
