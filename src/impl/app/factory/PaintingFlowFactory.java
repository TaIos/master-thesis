package factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Singleton;
import models.dto.PaintingFlowDto;
import models.entity.PaintingsFlow;

@Singleton
public class PaintingFlowFactory implements Factory<List<PaintingFlowDto>, PaintingsFlow> {

  @Override
  public PaintingsFlow create(List<PaintingFlowDto> dtos) {
    Map<String, Map<String, Double>> flow = new HashMap<>();
    dtos.forEach(
        dto -> {
          flow.computeIfAbsent(
              dto.getFrom(), k -> new HashMap<>()).put(dto.getTo(), dto.getFlow());
          flow.computeIfAbsent(
              dto.getTo(), k -> new HashMap<>()).put(dto.getFrom(), dto.getFlow());
        }
    );

    return new PaintingsFlow(flow);
  }

}
