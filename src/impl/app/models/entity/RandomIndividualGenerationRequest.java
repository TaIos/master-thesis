package models.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RandomIndividualGenerationRequest {

  private Integer maximumWildCardCount;
  private List<Painting> paintingSeq;
}
