package factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import exceptions.DtoConstraintViolationException;
import exceptions.DtoConstraintViolationExceptionWrapper;
import exceptions.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.dto.CreateComputationFromDatasetDto;
import models.dto.DatasetDto;
import models.dto.validation.DtoValidator;
import org.slf4j.Logger;

@Singleton
public class DatasetFactory implements Factory<String, DatasetDto> {

  private final File datasetDir;
  private final DtoValidator dtoValidator;
  private final Logger logger;
  private final ObjectMapper mapper;

  @Inject
  public DatasetFactory(
      Config config, DtoValidator dtoValidator, CustomLoggerFactory loggerFactory) {
    this.datasetDir = loadDatasetDirectory(config.getString("datasetDir"));
    this.dtoValidator = dtoValidator;
    this.logger = loggerFactory.create(DatasetFactory.class);
    this.mapper = new ObjectMapper();
  }

  @Override
  public DatasetDto create(String datasetName)
      throws EntityNotFoundException, DtoConstraintViolationExceptionWrapper {
    try {
      logger.info(
          "Loading dataset [{}] from directory [{}]", datasetName, datasetDir.getAbsolutePath());
      return dtoValidator.deserializeAndValidate(
          mapper.readTree(findOrThrow(datasetName)), DatasetDto.class);
    } catch (IOException e) {
      logger.error(e.toString());
      throw new RuntimeException(e);
    } catch (DtoConstraintViolationException e) {
      throw new DtoConstraintViolationExceptionWrapper(
          e, String.format("Dataset [%s] is in invalid format", datasetName));
    }
  }

  public DatasetDto create(CreateComputationFromDatasetDto dto)
      throws DtoConstraintViolationException, EntityNotFoundException,
      DtoConstraintViolationExceptionWrapper {
    return create(dto.getDatasetName());
  }

  private File findOrThrow(String datasetName) throws EntityNotFoundException, IOException {
    try (Stream<Path> stream = Files.walk(datasetDir.toPath())) {
      return stream.filter(p -> p.getFileName().toString().equals(datasetName)
              || p.getFileName().toString().equals(datasetName + ".json"))
          .findAny()
          .orElseThrow(() -> new EntityNotFoundException(DatasetDto.class, datasetName))
          .toFile();
    }
  }

  private File loadDatasetDirectory(String path) {
    File dir = new File(path);
    if (!dir.isDirectory()) {
      throw new IllegalArgumentException(
          String.format("[%s] does not exists or is not a directory", path));
    }
    return dir;
  }
}
