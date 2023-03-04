package models.entity;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Individual {

  protected List<Painting> paintingSeq;
  protected List<Double> paintingSeqRandomKey;
  protected List<Double> slicingOrderRandomKey;
  protected List<OrientationProbability> orientationProb;

}
