package models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Individual implements Comparable<Individual>, Cloneable {

  public static final double OBJECTIVE_VALUE_MAX = Double.MAX_VALUE;
  public static final double OBJECTIVE_VALUE_MIN = 0;

  private List<Facility> facilitySequence;
  private List<Integer> slicingOrder;
  private List<Orientation> orientations;
  @Builder.Default private Double objectiveValue = OBJECTIVE_VALUE_MAX;

  public Individual(
      List<Facility> facilitySequence, List<Integer> slicingOrder, List<Orientation> orientations) {
    this(facilitySequence, slicingOrder, orientations, OBJECTIVE_VALUE_MAX);
  }

  @Override
  public int compareTo(Individual individual) {
    return objectiveValue.compareTo(individual.objectiveValue);
  }

  @Override
  public Individual clone() {
    try {
      Individual clone = (Individual) super.clone();
      clone.facilitySequence =
          facilitySequence.stream().map(Facility::clone).collect(Collectors.toList());
      clone.slicingOrder = new ArrayList<>(slicingOrder);
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
        "objective: %.2f\nfacility sequence: %s\nslicing order: %s\norientation: %s",
        objectiveValue,
        facilitySequence.stream().map(Facility::getIdent).collect(Collectors.joining(",")),
        slicingOrder.stream().map(Object::toString).collect(Collectors.joining(",")),
        orientations.stream().map(o -> o.getType().getLabel()).collect(Collectors.joining(",")));
  }
}
