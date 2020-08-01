package ec.edu.espe.medicbyte.common.core;

import java.lang.annotation.*;

/**
 *
 * @author Jonathan Andres J.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(UIEvents.class)
public @interface UIEvent {
    String value();
}
