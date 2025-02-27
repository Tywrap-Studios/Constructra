/*
 * MIT License
 *
 * Copyright (c) 2025 Tywrap Studios;
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
