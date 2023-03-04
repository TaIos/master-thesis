package utils.checks;

import java.util.List;
import models.entity.Orientation.OrientationType;
import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;
import utils.annotations.SizeEqualToOrientationTypeCount;

public class SizeEqualToOrientationTypeCountCheck extends
    AbstractAnnotationCheck<SizeEqualToOrientationTypeCount> {


  /**
   * This method implements the validation logic
   *
   * @param validatedObject the object/bean to validate the value against, for static fields or
   *                        methods this is the class
   * @param valueToValidate the value to validate, may be null when validating pre conditions for
   *                        static methods
   * @param cycle
   * @return true if the value satisfies the checked constraint
   * @since 3.1.0
   */
  @Override
  public boolean isSatisfied(Object validatedObject, Object valueToValidate, ValidationCycle cycle)
      throws OValException {
    if (valueToValidate == null) {
      return true;
    }
    List<Double> weights = (List<Double>) valueToValidate;
    return weights.size() == OrientationType.values().length;
  }
}
