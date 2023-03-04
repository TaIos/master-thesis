package models.entity;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResolvedIndividual {

  private List<Painting> paintingSeqResolved;
  private List<Integer> slicingOrderResolved;
  private List<ResolvedOrientation> orientationResolved;

}
