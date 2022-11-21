package unit.factory.genetic_algorithm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import factory.EvaluatorFactory;
import factory.GAParametersFactory;
import factory.GeneticAlgorithmFactory;
import factory.InstanceParameterFactory;
import factory.MetricFactory;
import factory.ObjectiveFactory;
import factory.PlacingProvider;
import factory.RectangleFactory;
import factory.operators.MatingOperatorFactory;
import factory.operators.MutateOperatorFactory;
import factory.operators.SelectOperatorFactory;
import logic.genetic.Placing;
import logic.genetic.algorithm.BaseGeneticAlgorithm;
import logic.genetic.algorithm.GeneticAlgorithm;
import logic.objective.Objective;
import models.dto.CreateComputationDto;
import models.dto.validation.DtoValidator;
import models.entity.GAParameters;
import models.entity.Point;
import models.entity.Rectangle;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import services.RectangleService;
import utils.ResourceFileLoaderUtil;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GeneticAlgorithmFactoryTest implements ResourceFileLoaderUtil {

  private final DtoValidator dtoValidator;
  private final GeneticAlgorithmFactory geneticAlgorithmFactory;

  public GeneticAlgorithmFactoryTest() {
    dtoValidator = new DtoValidator();
    geneticAlgorithmFactory =
        new GeneticAlgorithmFactory(
            new GAParametersFactory(
                new MatingOperatorFactory(),
                new MutateOperatorFactory(),
                new SelectOperatorFactory()),
            new EvaluatorFactory(
                new ObjectiveFactory(new MetricFactory()),
                new PlacingProvider(new Placing(new RectangleService(new RectangleFactory()))),
                new InstanceParameterFactory(new RectangleFactory())));
  }

  private CreateComputationDto loadAndValidateCreateComputationDtoFromJsonFile() throws Exception {
    InputStream is = getResourceAsInputStream("/computationParameters.json");
    assertNotNull("JSON file used to create computation DTO must be present", is);
    String jsonTxt = IOUtils.toString(is, StandardCharsets.UTF_8);
    JsonNode json = new ObjectMapper().readTree(jsonTxt);
    return dtoValidator.deserializeAndValidate(json, CreateComputationDto.class);
  }

  @Test
  public void create_shouldSucceed() throws Exception {
    CreateComputationDto dto = loadAndValidateCreateComputationDtoFromJsonFile();
    GeneticAlgorithm gaInterface = geneticAlgorithmFactory.create(dto);
    assertNotNull(gaInterface);
    assertEquals("simpleGa", gaInterface.getType().getLabel());

    assertTrue(gaInterface instanceof BaseGeneticAlgorithm);
    BaseGeneticAlgorithm ga = (BaseGeneticAlgorithm) gaInterface;

    final double DELTA = 0.001;
    Rectangle r = ga.getEvaluator().getParams().getGrid();
    assertEquals(1, r.getX().intValue());
    assertEquals(2, r.getY().intValue());
    assertEquals(3, r.getWidth().intValue());
    assertEquals(4, r.getHeight().intValue());

    Point c = r.getCenter();
    assertEquals(2, c.getX(), DELTA);
    assertEquals(3, c.getY(), DELTA);

    GAParameters g = ga.getParams();
    assertEquals(0.1, g.getMutationProb(), DELTA);
    assertEquals(0.4, g.getCrossoverProb(), DELTA);
    assertEquals(300, g.getMaxNumberOfIter().intValue());
    assertEquals(100, g.getPopulationSize().intValue());
    assertEquals("repeatFirstParent", g.getMatingOperator().getType().getLabel());
    assertEquals("flipOneOrientationAtRandom", g.getMutateOperator().getType().getLabel());
    assertEquals("best", g.getSelectOperator().getType().getLabel());

    Objective o = ga.getEvaluator().getObjective();
    assertEquals("useMetricOnly", o.getType().getLabel());
    assertEquals("euclidean", o.getMetric().getType().getLabel());
  }
}
