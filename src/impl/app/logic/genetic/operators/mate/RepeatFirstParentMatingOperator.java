package logic.genetic.operators.mate;

import factory.copy_factory.IndividualCopyFactory;
import java.util.List;
import models.entity.Individual;

public class RepeatFirstParentMatingOperator implements MatingOperator {

  private final IndividualCopyFactory individualCopyFactory;

  public RepeatFirstParentMatingOperator(IndividualCopyFactory individualCopyFactory) {
    this.individualCopyFactory = individualCopyFactory;
  }

  @Override
  public List<Individual> mate(Individual p1, Individual p2) {
    return List.of(individualCopyFactory.createCopy(p1));
  }

  @Override
  public Type getType() {
    return Type.REPEAT_FIRST_PARENT;
  }
}
