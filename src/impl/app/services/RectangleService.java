package services;

import factory.RectangleFactory;
import models.entity.Rectangle;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class RectangleService {

  private final RectangleFactory rectangleFactory;

  @Inject
  public RectangleService(RectangleFactory rectangleFactory) {
    this.rectangleFactory = rectangleFactory;
  }

  public List<Rectangle> splitHorizontally(Rectangle r) {
    int h1 = (int) Math.ceil(r.getHeight() / 2f);
    int h2 = r.getHeight() - h1;
    return List.of(
        rectangleFactory.create(r.getX(), r.getY() + h1, r.getWidth(), h2),
        rectangleFactory.create(r.getX(), r.getY(), r.getWidth(), h1));
  }

  public List<Rectangle> splitVertically(Rectangle r) {
    int w1 = (int) Math.ceil(r.getWidth() / 2f);
    int w2 = r.getWidth() - w1;
    return List.of(
        rectangleFactory.create(r.getX(), r.getY(), w1, r.getHeight()),
        rectangleFactory.create(r.getX() + w1, r.getY(), w2, r.getHeight()));
  }
}
