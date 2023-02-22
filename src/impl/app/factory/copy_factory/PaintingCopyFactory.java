package factory.copy_factory;

import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.entity.Painting;

@Singleton
public class PaintingCopyFactory implements CopyFactory<Painting> {

  private final RectangleCopyFactory rectangleCopyFactory;
  private final PointCopyFactory pointCopyFactory;

  @Inject
  public PaintingCopyFactory(RectangleCopyFactory rectangleCopyFactory,
      PointCopyFactory pointCopyFactory) {
    this.rectangleCopyFactory = rectangleCopyFactory;
    this.pointCopyFactory = pointCopyFactory;
  }

  @Override
  public Painting createCopy(Painting p) {
    return Painting.builder()
        .ident(p.getIdent())
        .allocatedSpace(
            Optional.ofNullable(p.getAllocatedSpace())
                .map(rectangleCopyFactory::createCopy)
                .orElse(null)
        )
        .placement(
            Optional.ofNullable(p.getPlacement())
                .map(pointCopyFactory::createCopy)
                .orElse(null)
        )
        .width(p.getWidth())
        .height(p.getHeight())
        .area(p.getArea())
        .build();
  }
}
