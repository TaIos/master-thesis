package models.entity;

import logic.genetic.HallOfFame;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GAResult {

  private final HallOfFame hallOfFame;
}
