package models.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.oval.constraint.AssertValid;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import utils.annotations.SizeEqualToOrientationTypeCount;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GAParametersDto implements Dto {

  @NotNull
  @Min(1)
  private Integer maxNumberOfIter;

  @NotNull
  @Min(10)
  private Integer populationSize;

  @NotNull
  @Min(0)
  private Integer maximumWildCardCount;

  @NotNull
  @AssertValid
  private PopulationDivisionCountsDto populationDivisionCounts;

  @NotNull
  @AssertValid
  private InitialPopulationDivisionCountsDto initialPopulationDivisionCounts;

  @NotNull
  @NotBlank
  private String geneticAlgorithm;

  @NotNull
  @NotBlank
  private String evaluator;

  @NotNull
  @NotBlank
  private String placingHeuristics;

  @NotNull
  @NotBlank
  private String mate;

  @NotNull
  @NotBlank
  private String mutate;

  @NotNull
  @NotBlank
  private String select;

  @NotNull
  @MinSize(1)
  @SizeEqualToOrientationTypeCount
  private List<Double> orientationWeights;
}
