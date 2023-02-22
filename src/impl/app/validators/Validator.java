package validators;

import exceptions.BaseException;

public interface Validator<T> {


  void throwIfInvalid(T valueToValidate) throws BaseException;
}
