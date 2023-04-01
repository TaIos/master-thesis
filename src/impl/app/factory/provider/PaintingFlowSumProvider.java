package factory.provider;

import javax.inject.Provider;
import javax.inject.Singleton;
import logic.objective.parts.PaintingFlowSum;

@Singleton
public class PaintingFlowSumProvider implements
    Provider<PaintingFlowSum> {

  private final PaintingFlowSum paintingFlowSum;

  public PaintingFlowSumProvider() {
    paintingFlowSum = new PaintingFlowSum();
  }

  @Override
  public PaintingFlowSum get() {
    return paintingFlowSum;
  }
}
