package hsim.mapper.entity.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The interface Ignore update from obj.
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface IgnoreUpdateFromObj {
}
