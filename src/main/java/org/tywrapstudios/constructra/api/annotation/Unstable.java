package org.tywrapstudios.constructra.api.annotation;

import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;

/**
 * If a method, field, package or class is annotated with {@code @Unstable} logs may throw applicable warnings or errors to notify users or other developers that the feature they are attempting to use may be unstable and not ready for deployment.
 */
@Target(value = {METHOD, FIELD, PACKAGE, TYPE})
public @interface Unstable {
    /**
     * Returns as to why this feature is unstable, should preferably be as detailed as possible while keeping it short.
     * @return the reason for instability
     */
    String value();

    /**
     * Returns whether the unstable feature can critically break the runtime, all the way upto crashing it.
     * @return the criticality
     */
    boolean critical() default false;
}
