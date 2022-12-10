package models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GAResultDto implements Dto {

  private IndividualDto bestIndividual;
  private HallOfFameDto hallOfFame;
}
