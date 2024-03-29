package unit.factory.genetic_algorithm;

import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import factory.GeneticAlgorithmFactory;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import models.dto.CreateComputationDto;
import models.dto.validation.DtoValidator;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import utils.ResourceFileLoaderUtil;

public class GeneticAlgorithmFactoryTest implements ResourceFileLoaderUtil {

  private DtoValidator dtoValidator;
  private GeneticAlgorithmFactory geneticAlgorithmFactory;

  /*
  public GeneticAlgorithmFactoryTest() {
    dtoValidator = new DtoValidator();
    RandomProvider randomProvider = new RandomProvider(new Random(42));
    RectangleFactory rectangleFactory = new RectangleFactory();
    FlowFactory flowFactory = new FlowFactory();
    PaintingFactory facilityFactory = new PaintingFactory();
    geneticAlgorithmFactory =
        new GeneticAlgorithmFactory(
            new GAParametersFactory(
                new MatingOperatorFactory(randomProvider),
                new MutateOperatorFactory(randomProvider),
                new SelectOperatorFactory()),
            new InstanceParameterFactory(rectangleFactory, flowFactory, facilityFactory),
            new EvaluatorFactory(
                new ObjectiveFactory(new MetricFactory(), flowFactory),
                new PlacingProvider(
                    new RectangleService(rectangleFactory), new RandomKeyDecoderProvider()),
                new InstanceParameterFactory(rectangleFactory, flowFactory, facilityFactory)),
            new HallOfFameFactory(),
            new GeneratorProvider(randomProvider),
            randomProvider,
            new CustomLoggerFactory(),
            new RandomStringGenerator());
  }
  */

  private CreateComputationDto loadAndValidateCreateComputationDtoFromJsonFile() throws Exception {
    InputStream is = getResourceAsInputStream("/computationParameters.json");
    assertNotNull("JSON file used to create computation DTO must be present", is);
    String jsonTxt = IOUtils.toString(is, StandardCharsets.UTF_8);
    JsonNode json = new ObjectMapper().readTree(jsonTxt);
    return dtoValidator.deserializeAndValidate(json, CreateComputationDto.class);
  }

  @Test
  @Ignore("Not rewritten to new logic of dataset loading.")
  public void create_shouldSucceed() throws Exception {
    /*
    CreateComputationDto dto = loadAndValidateCreateComputationDtoFromJsonFile();
    GeneticAlgorithm gaInterface = geneticAlgorithmFactory.create(dto);
    assertNotNull(gaInterface);
    assertEquals("simpleGa", gaInterface.getType().getLabel());

    assertTrue(gaInterface instanceof BaseGeneticAlgorithm);
    BaseGeneticAlgorithm ga = (BaseGeneticAlgorithm) gaInterface;

    final double DELTA = 0.001;
    Rectangle r = ga.getEvaluator().getParams().getLayout().getBoundingRectangle();
    assertEquals(1, r.getX().intValue());
    assertEquals(2, r.getY().intValue());
    assertEquals(3, r.getWidth().intValue());
    assertEquals(4, r.getHeight().intValue());

    Point c = r.getCenter();
    assertEquals(2, c.getX(), DELTA);
    assertEquals(3, c.getY(), DELTA);

    GAParameters g = ga.getGaParams();
    assertEquals(0.1, g.getMutationProb(), DELTA);
    assertEquals(0.4, g.getCrossoverProb(), DELTA);
    assertEquals(300, g.getMaxNumberOfIter().intValue());
    assertEquals(100, g.getPopulationSize().intValue());
    assertEquals("repeatFirstParent", g.getMatingOperator().getType().getLabel());
    assertEquals("flipOneOrientationAtRandom", g.getMutateOperator().getType().getLabel());
    assertEquals("best", g.getSelectOperator().getType().getLabel());

    InstanceParameters p = ga.getInstanceParams();
//    assertEquals(7, p.getFacilityCount().intValue());

    Objective o = ga.getEvaluator().getObjective();
    assertEquals("useMetricOnly", o.getType().getLabel());
    assertEquals("euclidean", o.getMetric().getType().getLabel());
     */
  }
}
