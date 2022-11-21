package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.typesafe.config.Config;
import factory.ComputationResultFactory;
import factory.CustomLoggerFactory;
import factory.ExceptionalComputationResultFactory;
import models.entity.ComputationContext;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static play.libs.Json.toJson;

@Singleton
public class ResultWriterService {

  private final File outDir;
  private final Logger logger;
  private final ComputationResultFactory computationResultFactory;
  private final ExceptionalComputationResultFactory exceptionalComputationResultFactory;
  private final String computationalResultFileName;
  private final ObjectMapper mapper;

  @Inject
  public ResultWriterService(
      Config config,
      CustomLoggerFactory loggerFactory,
      ComputationResultFactory computationResultFactory,
      ExceptionalComputationResultFactory exceptionalComputationResultFactory) {
    this.logger = loggerFactory.create(ResultWriterService.class);
    this.outDir = createDirectory(config.getString("outputDirectory"));
    this.computationResultFactory = computationResultFactory;
    this.computationalResultFileName = config.getString("computationalResultFileName");
    this.exceptionalComputationResultFactory = exceptionalComputationResultFactory;

    this.mapper = new ObjectMapper();
    this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  public synchronized File createDirForComputationOutput(String baseName) {
    return createDirectory(
        outDir.getAbsolutePath() + File.separator + getNextPrefix() + "_" + baseName);
  }

  public void writeComputationResult(ComputationContext context) {
    if (!context.getResultDir().exists()) createDirectory(context.getResultDir().getAbsolutePath());
    writeJsonToFile(
        getComputationalResultFileNamePathAbs(context) + ".json",
        toJson(computationResultFactory.create(context)),
        context.getLogger());
  }

  public void writeExceptionalResult(ComputationContext context, Throwable e) {
    writeJsonToFile(
        getComputationalResultFileNamePathAbs(context) + ".json",
        toJson(exceptionalComputationResultFactory.create(context, e)),
        context.getLogger());
  }

  private void writeJsonToFile(String absolutePath, JsonNode json, Logger logger) {
    try {
      mapper.writeValue(new File(absolutePath), json);
      logger.info("Successfully written to file [{}]", absolutePath);
    } catch (IOException e) {
      logger.error("Failed writing to file [{}] with error [{}]", absolutePath, e);
    }
  }

  private String getNextPrefix() {
    Optional<String[]> fileNameArrOp =
        Optional.ofNullable(outDir.list((dir, fileName) -> fileName.matches("^[0-9]+_.+")));
    int currentMaxPrefix =
        Arrays.stream(fileNameArrOp.orElse(new String[] {}))
            .mapToInt(fileName -> Integer.parseInt(fileName.substring(0, fileName.indexOf("_"))))
            .max()
            .orElse(0);
    return String.format("%03d", currentMaxPrefix + 1);
  }

  private File createDirectory(String absolutePath) {
    File dir = new File(absolutePath);
    if (!dir.exists()) {
      if (dir.mkdirs()) {
        logger.info("Successfully created directory [{}]", dir.getAbsolutePath());
      } else {
        logger.error("Failed to created directory [{}]", dir.getAbsolutePath());
      }
    } else {
      logger.info("Directory already exists [{}]", dir.getAbsolutePath());
    }
    return dir;
  }

  private String getComputationalResultFileNamePathAbs(ComputationContext context) {
    return context.getResultDir().getAbsolutePath() + File.separator + computationalResultFileName;
  }
}
