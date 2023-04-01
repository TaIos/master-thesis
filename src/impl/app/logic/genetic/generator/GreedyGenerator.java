package logic.genetic.generator;

import java.util.ArrayList;
import java.util.List;
import logic.genetic.evaluator.Evaluator;
import logic.genetic.generator.Generator;
import models.entity.Individual;
import models.entity.Painting;
import models.entity.Population;

public class GreedyGenerator {

  private final Generator generator;

  public GreedyGenerator(Generator generator) {
    this.generator = generator;
  }

  public List<Individual> generate(List<Painting> paintings, int size, Evaluator evaluator) {
    List<Individual> res = new ArrayList<>(size);

    for (int i = 0; i < size; i++) {
      res.add(
          new Population(
              generator.generateRandomIndividualList(paintings, 100), // TODO parametrize
              evaluator)
              .getBestIndividual().getIndividual());
    }
    return res;
  }
}
