package models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import utils.serialization.DoubleListSerializer;
import utils.serialization.DoubleSerializer;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class IndividualDto implements Dto {
  private Integer iteration;

  @JsonSerialize(using = DoubleSerializer.class)
  private Double objectiveValue;

  private List<String> facilitySequence;
  private List<String> facilitySequenceDecoded;

  @JsonSerialize(using = DoubleListSerializer.class)
  private List<Double> facilitySequenceRandomKey;

  private List<Integer> slicingOrderDecoded;

  @JsonSerialize(using = DoubleListSerializer.class)
  private List<Double> slicingOrderRandomKey;

  private List<String> orientations;

  @JsonProperty("facilityPlacement_XYWH")
  private List<String> facilityPlacement;
}
