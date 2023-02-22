package factory.copy_factory;

import javax.inject.Singleton;
import models.entity.Point;

@Singleton
public class PointCopyFactory implements CopyFactory<Point> {

  @Override
  public Point createCopy(Point p) {
    return Point.builder()
        .x(p.getX())
        .y(p.getY())
        .build();
  }
}
