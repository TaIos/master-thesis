package factory;

import factory.copy_factory.IndividualCopyFactory;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.entity.BestIndividual;
import models.entity.Individual;
import models.entity.Population;

@Singleton
public class BestIndividualFactory implements Factory<Individual, BestIndividual> {

  private final IndividualCopyFactory individualCopyFactory;

  @Inject
  public BestIndividualFactory(IndividualCopyFactory individualCopyFactory) {
    this.individualCopyFactory = individualCopyFactory;
  }

  @Override
  public BestIndividual create(Individual individual) {
    return create(individual, 0);
  }

  public BestIndividual create(Population population) {
    return create(population.getBestIndividual(), 0);
  }

  public BestIndividual create(Population population, int iteration) {
    return create(population.getBestIndividual(), iteration);
  }

  public BestIndividual create(Individual individual, int iteration) {
    return BestIndividual.builder()
        .iteration(iteration)
        .individual(individualCopyFactory.createCopy(individual))
        .build();
  }

  public BestIndividual createUpdated(BestIndividual original, Population population,
      int iteration) {
    if (population.getBestIndividual().getObjectiveValue() < original.getIndividual()
        .getObjectiveValue()) {
      return create(population, iteration);
    }
    return original;
  }
}
