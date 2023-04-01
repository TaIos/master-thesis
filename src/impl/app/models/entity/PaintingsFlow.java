package models.entity;

import java.util.Collections;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaintingsFlow {

  private final Map<String, Map<String, Double>> flow;

  public Double between(Painting p1, Painting p2) {
    return flow.getOrDefault(p1.getIdent(), Collections.emptyMap())
        .getOrDefault(p2.getIdent(), 0d);
  }

}
