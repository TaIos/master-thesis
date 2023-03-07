package factory.operators;

import static logic.genetic.operators.mutate.MutateOperator.Type;

import exceptions.EntityNotFoundException;
import exceptions.ImplementationNotFoundException;
import factory.Factory;
import factory.provider.RandomProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import logic.genetic.operators.mutate.FlipOnePartAtRandom;
import logic.genetic.operators.mutate.FlipOrientationProbability;
import logic.genetic.operators.mutate.FlipPaintingSequence;
import logic.genetic.operators.mutate.FlipSlicingOrder;
import logic.genetic.operators.mutate.MutateOperator;
import models.dto.GAParametersDto;

@Singleton
public class MutateOperatorFactory implements Factory<String, MutateOperator> {

  private final RandomProvider randomProvider;

  @Inject
  public MutateOperatorFactory(RandomProvider randomProvider) {
    this.randomProvider = randomProvider;
  }

  public MutateOperator create(GAParametersDto dto)
      throws EntityNotFoundException, ImplementationNotFoundException {
    return create(dto.getMutate());
  }

  @Override
  public MutateOperator create(String name)
      throws EntityNotFoundException, ImplementationNotFoundException {

    switch (findOrThrow(name)) {
      case FLIP_SLICING_ORDER:
        return new FlipSlicingOrder(randomProvider.get());
      case FLIP_PAINTING_SEQUENCE:
        return new FlipPaintingSequence(randomProvider.get());
      case FLIP_ORIENTATION:
        return new FlipOrientationProbability(randomProvider.get());
      case FLIP_ONE_PART_AT_RANDOM:
        return new FlipOnePartAtRandom(randomProvider.get(),
            new FlipOrientationProbability(randomProvider.get()),
            new FlipPaintingSequence(randomProvider.get()),
            new FlipSlicingOrder(randomProvider.get())
        );
      default:
        throw new ImplementationNotFoundException(MutateOperator.class, name);
    }
  }

  private Type findOrThrow(String name) throws EntityNotFoundException {
    return Type.getForLabel(name)
        .orElseThrow(() -> new EntityNotFoundException(MutateOperator.class, name, Type.values()));
  }
}
