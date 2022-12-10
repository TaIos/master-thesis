package factory.validators;

import exceptions.FunctionNotValidException;
import javax.inject.Singleton;
import org.mariuszgromada.math.mxparser.Function;

@Singleton
public class MxparserFunctionValidator implements Validator<Function> {

  @Override
  public void throwIfInvalid(Function func) throws FunctionNotValidException {
    throwIfInvalidSyntax(func);
    throwIfNotTwoArgumentFunction(func);
  }

  private void throwIfInvalidSyntax(Function func) throws FunctionNotValidException {
    if (!func.checkSyntax()) {
      throw new FunctionNotValidException(
          String.format("Function [%s] has invalid syntax", func));
    }
  }


  private void throwIfNotTwoArgumentFunction(Function func) throws FunctionNotValidException {
    if (func.getArgumentsNumber() != 2) {
      throw new FunctionNotValidException(
          String.format("Function [%s] must have exactly 2 arguments; it has %s", func,
              func.getArgumentsNumber()));
    }
  }
}
