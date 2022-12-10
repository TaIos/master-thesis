package models.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Individual implements Comparable<Individual>, Cloneable {

  public static final double OBJECTIVE_VALUE_MAX = Double.MAX_VALUE;
  public static final double OBJECTIVE_VALUE_MIN = 0;

  private List<Painting> paintingSeq;
  private List<Double> paintingSeqRandomKey;
  private List<Double> slicingOrderRandomKey;
  private List<Orientation> orientations;
  @Builder.Default
  private Double objectiveValue = OBJECTIVE_VALUE_MAX;

  @Override
  public int compareTo(Individual individual) {
    return objectiveValue.compareTo(individual.objectiveValue);
  }

  // FIXME rewrite clone as factory
  @Override
  public Individual clone() {
    try {
      Individual clone = (Individual) super.clone();
      clone.paintingSeq =
          paintingSeq.stream().map(Painting::clone).collect(Collectors.toList());
      clone.paintingSeqRandomKey = new ArrayList<>(paintingSeqRandomKey);
      clone.slicingOrderRandomKey = new ArrayList<>(slicingOrderRandomKey);
      clone.orientations = new ArrayList<>(orientations);
      clone.objectiveValue = objectiveValue;
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }

  @Override
  public String toString() {
    return String.format(
        "objective: %.2f\npainting sequence: %s\nslicing order random keys: %s\norientation: %s",
        objectiveValue,
        paintingSeq.stream().map(Painting::getIdent).collect(Collectors.joining(",")),
        slicingOrderRandomKey.stream().map(Object::toString).collect(Collectors.joining(",")),
        orientations.stream().map(o -> o.getType().getLabel()).collect(Collectors.joining(",")));
  }
}
