package factory;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import models.dto.CreateComputationDto;
import models.dto.InstanceParametersDto;
import models.entity.InstanceParameters;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InstanceParameterFactory
    implements Factory<InstanceParametersDto, InstanceParameters> {

  private final RectangleFactory rectangleFactory;

  @Inject
  public InstanceParameterFactory(RectangleFactory rectangleFactory) {
    this.rectangleFactory = rectangleFactory;
  }

  @Override
  public InstanceParameters create(InstanceParametersDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return InstanceParameters.builder().grid(rectangleFactory.create(dto)).build();
  }

  public InstanceParameters create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return create(dto.getInstanceParams());
  }
}
