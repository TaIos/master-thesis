package models.entity;

import logic.genetic.HallOfFame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GAResult {

  private HallOfFame hallOfFame;
}
