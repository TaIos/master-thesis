package factory;

import models.dto.FacilityFlowDto;
import models.dto.InstanceParametersDto;
import models.entity.Flow;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class FlowFactory implements Factory<List<FacilityFlowDto>, Flow> {
  @Override
  public Flow create(List<FacilityFlowDto> dtos) {
    Map<String, Map<String, Double>> flow = new HashMap<>();
    dtos.forEach(
        dto -> {
          flow.computeIfAbsent(
              dto.getFrom(), k -> new HashMap<>(Map.of(dto.getTo(), dto.getFlow())));
          flow.computeIfAbsent(
              dto.getTo(), k -> new HashMap<>(Map.of(dto.getFrom(), dto.getFlow())));
        });
    return new Flow(flow);
  }

  public Flow create(InstanceParametersDto dto) {
    return create(dto.getFlow());
  }
}
