package factory;

import exceptions.FunctionNotValidException;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.mariuszgromada.math.mxparser.Function;
import validators.MxparserFunctionValidator;

@Singleton
public class MxparserFunctionFactory implements Factory<String, Function> {

  private final MxparserFunctionValidator mxparserFunctionValidator;

  @Inject
  public MxparserFunctionFactory(MxparserFunctionValidator mxparserFunctionValidator) {
    this.mxparserFunctionValidator = mxparserFunctionValidator;
  }

  @Override
  public Function create(String funcStr) throws FunctionNotValidException {
    Function func = new Function(funcStr);
    mxparserFunctionValidator.throwIfInvalid(func);
    return func;
  }
}
