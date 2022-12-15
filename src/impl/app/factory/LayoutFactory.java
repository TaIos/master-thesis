package factory;

import exceptions.FunctionNotValidException;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.dto.LayoutDto;
import models.entity.Layout;

@Singleton
public class LayoutFactory implements Factory<LayoutDto, Layout> {

  private final RectangleFactory rectangleFactory;
  private final MxparserFunctionFactory mxparserFunctionFactory;

  @Inject
  public LayoutFactory(RectangleFactory rectangleFactory,
      MxparserFunctionFactory mxparserFunctionFactory) {
    this.rectangleFactory = rectangleFactory;
    this.mxparserFunctionFactory = mxparserFunctionFactory;
  }

  @Override
  public Layout create(LayoutDto dto) throws FunctionNotValidException {
    return Layout.builder()
        .boundingRectangle(rectangleFactory.create(0, 0, dto.getWidth(), dto.getHeight()))
        .evalFunc(mxparserFunctionFactory.create(dto.getEvalFunc()))
        .build();
  }
}
