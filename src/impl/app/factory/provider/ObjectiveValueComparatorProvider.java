package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.objective.ObjectiveValueComparator;

@Singleton
public class ObjectiveValueComparatorProvider implements
    Provider<ObjectiveValueComparator> {

  private final ObjectiveValueComparator objectiveValueComparator;

  public ObjectiveValueComparatorProvider() {
    this.objectiveValueComparator = new ObjectiveValueComparator();
  }

  @Override
  public ObjectiveValueComparator get() {
    return objectiveValueComparator;
  }
}
