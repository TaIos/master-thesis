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
    return splitHorizontally(r, 0.5);
  }

  public List<Rectangle> splitVertically(Rectangle r) {
    return splitVertically(r, 0.5);
  }

  public List<Rectangle> splitHorizontally(Rectangle r, double ratio) {
    int h1 = (int) Math.ceil(r.getHeight() * ratio);
    int h2 = r.getHeight() - h1;
    return List.of(
        rectangleFactory.create(r.getX(), r.getY(), r.getWidth(), h1),
        rectangleFactory.create(r.getX(), r.getY() + h1, r.getWidth(), h2));
  }

  public List<Rectangle> splitVertically(Rectangle r, double ratio) {
    int w1 = (int) Math.ceil(r.getWidth() * ratio);
    int w2 = r.getWidth() - w1;
    return List.of(
        rectangleFactory.create(r.getX(), r.getY(), w1, r.getHeight()),
        rectangleFactory.create(r.getX() + w1, r.getY(), w2, r.getHeight()));
  }
}
