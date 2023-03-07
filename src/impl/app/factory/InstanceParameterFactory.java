package factory;

import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.dto.InstanceParametersDto;
import models.entity.InstanceParameters;

@Singleton
public class InstanceParameterFactory
    implements Factory<InstanceParametersDto, InstanceParameters> {

  private final PaintingFactory paintingFactory;
  private final LayoutFactory layoutFactory;

  @Inject
  public InstanceParameterFactory(PaintingFactory paintingFactory, LayoutFactory layoutFactory) {
    this.paintingFactory = paintingFactory;
    this.layoutFactory = layoutFactory;
  }


  @Override
  public InstanceParameters create(InstanceParametersDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException, FunctionNotValidException {
    return InstanceParameters.builder()
        .layout(layoutFactory.create(dto.getLayout()))
        .paintings(
            dto.getPaintings().stream().map(paintingFactory::create).collect(Collectors.toList()))
        .build();
  }
}
