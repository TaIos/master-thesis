package factory;

import exceptions.DtoConstraintViolationException;
import exceptions.EntityNotFoundException;
import exceptions.FunctionNotValidException;
import exceptions.ImplementationNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.dto.InstanceParametersDto;
import models.entity.InstanceParameters;
import models.entity.Painting;

@Singleton
public class InstanceParameterFactory
    implements Factory<InstanceParametersDto, InstanceParameters> {

  private final PaintingFactory paintingFactory;
  private final LayoutFactory layoutFactory;
  private final PaintingFlowFactory paintingFlowFactory;

  @Inject
  public InstanceParameterFactory(PaintingFactory paintingFactory, LayoutFactory layoutFactory,
      PaintingFlowFactory paintingFlowFactory) {
    this.paintingFactory = paintingFactory;
    this.layoutFactory = layoutFactory;
    this.paintingFlowFactory = paintingFlowFactory;
  }


  @Override
  public InstanceParameters create(InstanceParametersDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException, FunctionNotValidException, DtoConstraintViolationException {
    return InstanceParameters.builder()
        .layout(layoutFactory.create(dto.getLayout()))
        .paintings(createPaintings(dto))
        .flow(paintingFlowFactory.create(dto.getPaintingsFlow()))
        .build();
  }

  private List<Painting> createPaintings(InstanceParametersDto dto)
      throws DtoConstraintViolationException {
    List<Painting> paintings = dto.getPaintings().stream()
        .map(paintingFactory::create)
        .collect(Collectors.toList());
    throwIfIdentsNotUnique(paintings);
    return paintings;
  }

  private void throwIfIdentsNotUnique(List<Painting> paintings)
      throws DtoConstraintViolationException {
    List<String> idents = paintings.stream().map(Painting::getIdent).collect(Collectors.toList());

    if (idents.size() != new HashSet<>(idents).size()) {
      throw new DtoConstraintViolationException(
          String.format("Painting identifiers should be unique: [%s]", String.join(",", idents))
      );
    }
  }

}
