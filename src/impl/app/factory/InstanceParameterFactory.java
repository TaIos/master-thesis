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
  private final FlowFactory flowFactory;
  private final FacilityFactory facilityFactory;

  @Inject
  public InstanceParameterFactory(RectangleFactory rectangleFactory, FlowFactory flowFactory, FacilityFactory facilityFactory) {
    this.rectangleFactory = rectangleFactory;
    this.flowFactory = flowFactory;
    this.facilityFactory = facilityFactory;
  }

  @Override
  public InstanceParameters create(InstanceParametersDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return InstanceParameters.builder()
        .layout(rectangleFactory.create(dto))
        .facilityCount(dto.getFacilityCount())
        .emptySpace(dto.getEmptySpace())
        .facilities(facilityFactory.create(dto))
        .flow(flowFactory.create(dto))
        .build();
  }

  public InstanceParameters create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return create(dto.getInstanceParams());
  }
}
