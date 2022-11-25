package models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class IndividualDto implements Dto {
  private Integer iteration;
  private List<String> facilitySequence;
  private List<Integer> slicingOrder;
  private List<String> orientations;
  private Double objectiveValue;

  @JsonProperty("facilityPlacement_XYWH")
  private List<String> facilityPlacement;
}
