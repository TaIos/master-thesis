package utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.sf.oval.configuration.annotation.Constraint;
import utils.checks.SizeEqualToOrientationTypeCountCheck;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Constraint(checkWith = SizeEqualToOrientationTypeCountCheck.class)
public @interface SizeEqualToOrientationTypeCount {


  String message() default "Weight vector must have the same number of elements as there are orientation types";


}
