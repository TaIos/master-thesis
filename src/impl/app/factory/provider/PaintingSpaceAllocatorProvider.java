package factory.provider;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import logic.placing.PaintingSpaceAllocator;
import services.RectangleService;

@Singleton
public class PaintingSpaceAllocatorProvider implements Provider<PaintingSpaceAllocator> {

  private final PaintingSpaceAllocator paintingSpaceAllocator;

  @Inject
  public PaintingSpaceAllocatorProvider(
      RectangleService rectangleService, RandomKeyDecoderProvider decoderProvider) {
    this.paintingSpaceAllocator = new PaintingSpaceAllocator(rectangleService, decoderProvider.get());
  }

  @Override
  public PaintingSpaceAllocator get() {
    return paintingSpaceAllocator;
  }
}
