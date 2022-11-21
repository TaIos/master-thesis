package models.entity;

import logic.genetic.algorithm.GeneticAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import models.dto.CreateComputationDto;
import org.slf4j.Logger;

import java.io.File;

@Data
@Builder
@AllArgsConstructor
public class ComputationContext {
  private String id;
  private CreateComputationDto createComputationDto;
  private GeneticAlgorithm geneticAlgorithm;
  private ComputationResult computationResult;
  private File resultDir;
  private Logger logger;
}
