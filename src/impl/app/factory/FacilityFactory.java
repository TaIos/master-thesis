package factory;

import models.dto.FacilityDimensionDto;
import models.dto.InstanceParametersDto;
import models.entity.Facility;

import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class FacilityFactory implements Factory<FacilityDimensionDto, Facility> {
  @Override
  public Facility create(FacilityDimensionDto dto) {
    return Facility.builder().ident(dto.getFacility()).area(dto.getArea()).build();
  }

  public List<Facility> create(InstanceParametersDto dto) {
    return dto.getDimension().stream().map(this::create).collect(Collectors.toList());
  }
}
