package models.entity;

import java.util.Collections;
import java.util.Map;

public class Flow {

  private final Map<String, Map<String, Double>> flow;

  public Flow(Map<String, Map<String, Double>> flow) {
    this.flow = flow;
  }

  public Double between(Facility f1, Facility f2) {
    return flow.getOrDefault(f1.getIdent(), Collections.emptyMap()).getOrDefault(f2.getIdent(), 0d);
  }
}
