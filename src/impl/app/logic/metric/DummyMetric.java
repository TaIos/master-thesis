package logic.metric;

import models.entity.Facility;

public class DummyMetric implements Metric {

  @Override
  public Double eval(Facility f1, Facility f2) {
    return (double) (Integer.parseInt(f1.getIdent()) * Integer.parseInt(f2.getIdent()));
  }

  @Override
  public Type getType() {
    return Type.DUMMY;
  }
}
