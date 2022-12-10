package factory;

import javax.inject.Singleton;
import models.dto.RectangleDto;
import models.entity.Rectangle;

@Singleton
public class RectangleFactory implements Factory<RectangleDto, Rectangle> {

  @Override
  public Rectangle create(RectangleDto dto) {
    return create(dto.getX(), dto.getY(), dto.getWidth(), dto.getHeight());
  }

  public Rectangle create(int x, int y, int width, int height) {
    return Rectangle.builder().x(x).y(y).width(width).height(height).build();
  }
}
