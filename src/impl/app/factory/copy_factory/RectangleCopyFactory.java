package factory.copy_factory;

import javax.inject.Singleton;
import models.entity.Rectangle;

@Singleton
public class RectangleCopyFactory implements CopyFactory<Rectangle> {

  @Override
  public Rectangle createCopy(Rectangle r) {
    return Rectangle.builder()
        .x(r.getX())
        .y(r.getY())
        .width(r.getWidth())
        .height(r.getHeight())
        .build();
  }
}
