package models.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;
import utils.annotations.SizeEqualToOrientationTypeCount;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GAParametersDto implements Dto {

  @Min(0)
  @Max(1)
  @NotNull
  private Double mutationProb;

  @Min(0)
  @Max(1)
  @NotNull
  private Double crossoverProb;

  @NotNull
  @Min(1)
  private Integer maxNumberOfIter;

  @NotNull
  @Min(2)
  private Integer populationSize;

  @NotNull
  @NotBlank
  private String geneticAlgorithm;

  @NotNull
  @NotBlank
  private String evaluator;

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
