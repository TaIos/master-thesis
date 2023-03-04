package factory;

import javax.inject.Singleton;
import models.dto.PaintingDto;
import models.entity.Painting;

@Singleton
public class PaintingFactory implements Factory<PaintingDto, Painting> {

  @Override
  public Painting create(PaintingDto dto) {
    return Painting.builder()
        .ident(dto.getIdent())
        .width(dto.getWidth())
        .height(dto.getHeight())
        .build();
  }
}
