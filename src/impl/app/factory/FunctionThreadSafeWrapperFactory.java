package factory;

import exceptions.FunctionNotValidException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.dto.CreateComputationDto;
import org.mariuszgromada.math.mxparser.Function;
import utils.FunctionThreadSafeWrapper;

@Singleton
public class FunctionThreadSafeWrapperFactory implements
    Factory<CreateComputationDto, FunctionThreadSafeWrapper> {

  private final MxparserFunctionFactory mxparserFunctionFactory;

  @Inject
  public FunctionThreadSafeWrapperFactory(MxparserFunctionFactory mxparserFunctionFactory) {
    this.mxparserFunctionFactory = mxparserFunctionFactory;
  }

  @Override
  public FunctionThreadSafeWrapper create(CreateComputationDto dto)
      throws FunctionNotValidException {
    int capacity = (int) (1.2 * calculateMinimalFunctionCount(dto));
    List<Function> functions = new ArrayList<>(capacity);
    Function functionToCopy = mxparserFunctionFactory.create(
        dto.getInstanceParameters().getLayout().getEvalFunc());
    for (int i = 0; i < capacity; i++) {
      functions.add(functionToCopy.cloneForThreadSafe());
    }
    return new FunctionThreadSafeWrapper(functions);
  }

  private int calculateMinimalFunctionCount(CreateComputationDto dto) {
    return dto.getGaParameters().getPopulationSize() *
        2 ^ (dto.getInstanceParameters().getPaintings().size() - 1);
  }
}
