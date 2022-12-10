package logic.metric;

import models.entity.Painting;

public class DummyMetric implements Metric {

  @Override
  public Double eval(Painting p1, Painting p2) {
    return (double) (Integer.parseInt(p1.getIdent()) * Integer.parseInt(p2.getIdent()));
  }

  @Override
  public Type getType() {
    return Type.DUMMY;
  }
}
