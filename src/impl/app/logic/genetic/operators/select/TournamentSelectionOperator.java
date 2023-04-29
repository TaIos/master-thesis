package logic.genetic.operators.select;

import java.util.ArrayList;
import java.util.List;
import logic.genetic.evaluator.Evaluator;
import logic.genetic.generator.GreedyGenerator;
import models.entity.EvaluatedIndividual;
import models.entity.Individual;
import models.entity.Population;

public class TournamentSelectionOperator implements SelectOperator {

  private final GreedyGenerator greedyGenerator;

  public TournamentSelectionOperator(GreedyGenerator greedyGenerator) {
    this.greedyGenerator = greedyGenerator;
  }


  @Override
  public List<Individual> select(List<Individual> individuals, int size, Evaluator evaluator) {
//    assert (individuals.size() >= size); HOTFIX
    List<Individual> winners = new ArrayList<>(size);

    List<EvaluatedIndividual> contenders = new Population(individuals, evaluator)
        .getEvaluatedIndividuals();
    List<EvaluatedIndividual> greedy = new Population(
        greedyGenerator.generate(individuals.get(0).getPaintingSeq(), individuals.size(),
            evaluator),
        evaluator).getEvaluatedIndividuals();

    int contenderIdx = 0;
    int greedyIdx = 0;

    for (int i = 0; i < size; i++) {
      EvaluatedIndividual contenderInd = contenders.get(
          contenderIdx % contenders.size());// HOTFIX modulo
      EvaluatedIndividual greedyInd = greedy.get(greedyIdx % greedy.size()); // HOTFIX modulo

      if (evaluator.getObjectiveValueComparator()
          .compare(contenderInd.getLayout(), greedyInd.getLayout()) < 0) {
        winners.add(contenderInd.getIndividual());
        contenderIdx += 1;
      } else {
        winners.add(greedyInd.getIndividual());
        greedyIdx += 1;
      }
    }
    return winners;
  }

  @Override
  public Type getType() {
    return Type.TOURNAMENT;
  }
}
