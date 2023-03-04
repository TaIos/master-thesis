package factory;

import static utils.DelayedFormatter.format;
import static utils.JavaUtils.formatAsHumanReadableDuration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import logic.genetic.algorithm.GeneticAlgorithm;
import models.entity.ComputationContext;
import models.entity.ComputationResult;
import models.entity.GATimeMeasureWrapper;

@Singleton
public class GATimeMeasureWrapperFactory
    implements Factory<GeneticAlgorithm, GATimeMeasureWrapper> {

  @Override
  public GATimeMeasureWrapper create(GeneticAlgorithm geneticAlgorithm) {
    return new GATimeMeasureWrapper(geneticAlgorithm);
  }

  public CompletableFuture<ComputationContext> createAsCompletableFuture(
      ComputationContext context) {
    return CompletableFuture.supplyAsync(
        () -> {
          GATimeMeasureWrapper wrapper = create(context.getGeneticAlgorithm());
          context.getLogger().info("Starting computation");
          ComputationResult result = wrapper.call();
          context.setComputationResult(result);
          context
              .getLogger()
              .info(
                  "Computation finished in {}, best={} at iteration {}",
                  formatAsHumanReadableDuration(result.getDurationMillis(), TimeUnit.MILLISECONDS),
                  format("%.02f", context.getComputationResult().getGaResult().getHallOfFame()
                      .getBestRecord().getBestIndividual().getLayout().getObjectiveValue()),
                  context.getComputationResult().getGaResult().getHallOfFame().getBestRecord()
                      .getIteration());
          return context;
        });
  }
}
