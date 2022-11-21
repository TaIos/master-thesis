package factory;

import logic.genetic.algorithm.GeneticAlgorithm;
import models.entity.ComputationContext;
import models.entity.ComputationResult;
import models.entity.GATimeMeasureWrapper;

import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static utils.JavaUtils.formatAsHumanReadableDuration;

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
                  "Computation done in {}",
                  formatAsHumanReadableDuration(result.getDurationMillis(), TimeUnit.MILLISECONDS));
          return context;
        });
  }
}
