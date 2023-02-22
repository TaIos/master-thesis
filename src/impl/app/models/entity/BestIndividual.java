package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BestIndividual {

  private int iteration;
  private Individual individual;

}
