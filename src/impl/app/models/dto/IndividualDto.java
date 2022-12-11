package models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import utils.serialization.DoubleListSerializer;
import utils.serialization.DoubleSerializer;

@Data
@Builder
@AllArgsConstructor
public class IndividualDto implements Dto {

  private Integer iteration;

  @JsonSerialize(using = DoubleSerializer.class)
  private Double objectiveValue;

  private List<String> paintingSeq;
  private List<String> paintingSeqDecoded;

  @JsonSerialize(using = DoubleListSerializer.class)
  private List<Double> paintingSeqRandomKey;

  private List<Integer> slicingOrderDecoded;

  @JsonSerialize(using = DoubleListSerializer.class)
  private List<Double> slicingOrderRandomKey;

  private List<String> orientations;

  private List<String> orientationsResolved;

  @JsonProperty("paintingPlacement_XYWH")
  private List<String> paintingPlacement;
}
