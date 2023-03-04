package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.objective.parts.IsOutsideOfAllocatedArea;

@Singleton
public class IsOutsideOfAllocatedAreaObjectivePartProvider implements
    Provider<IsOutsideOfAllocatedArea> {

  private final IsOutsideOfAllocatedArea isOutsideOfAllocatedArea;

  public IsOutsideOfAllocatedAreaObjectivePartProvider() {
    isOutsideOfAllocatedArea = new IsOutsideOfAllocatedArea();
  }


  @Override
  public IsOutsideOfAllocatedArea get() {
    return isOutsideOfAllocatedArea;
  }
}
