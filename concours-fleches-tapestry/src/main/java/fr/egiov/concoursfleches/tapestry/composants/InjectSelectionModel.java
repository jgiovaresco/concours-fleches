package fr.egiov.concoursfleches.tapestry.composants;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
public @interface InjectSelectionModel {

   String labelField() default "null";

   String idField() default "null";

}
