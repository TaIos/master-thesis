package factory.copy_factory;

import java.util.ArrayList;
import javax.inject.Singleton;
import models.entity.OrientationProbability;

@Singleton
public class OrientationProbabilityCopyFactory implements CopyFactory<OrientationProbability> {

  @Override
  public OrientationProbability createCopy(OrientationProbability prob) {
    return new OrientationProbability(new ArrayList<>(prob.getProbabilityVector()));
  }
}
