package factory;

import exceptions.FunctionNotValidException;
import factory.validators.MxparserFunctionValidator;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.mariuszgromada.math.mxparser.Function;

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
