package factory;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import javax.inject.Singleton;
import logic.objective.Objective;
import logic.objective.SimpleObjective;
import models.dto.CreateComputationDto;

@Singleton
public class ObjectiveFactory implements Factory<String, Objective> {


  @Override
  public Objective create(String name)
      throws ImplementationNotFoundException, EntityNotFoundException {
    switch (findOrThrow(name)) {
      case SIMPLE:
        return new SimpleObjective();
      default:
        throw new ImplementationNotFoundException(Objective.class,
            name);
    }
  }

  public Objective create(CreateComputationDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return create(dto.getGaParams().getObjective());
  }

  private Objective.Type findOrThrow(String name) throws EntityNotFoundException {
    return Objective.Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(Objective.class, name));
  }
}
