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

package org.tywrapstudios.constructra.api.math.ruleset;

import org.tywrapstudios.constructra.api.math.exception.InvalidCalculationException;

import java.util.function.UnaryOperator;

/**
 * Because the world has differences and people can't make up their mind, different unit systems exist.
 * For the sake of these differences, have a class that has them and can convert from and to them.
 */
public class Units {

    /**
     * Converts a double from one Measurement to another, this method tries to be as accurate as possible.
     * @param d the original number to convert
     * @param from the Measurement that the double is in
     * @param to the Measurement the double should be converted into
     * @return the converted double
     * @throws InvalidCalculationException if something goes wrong
     */
    public static double convert(double d, Measurement from, Measurement to) throws InvalidCalculationException {
        if (from.type != to.type) throw new InvalidCalculationException(String.format("Tried to convert units of inconvertible types %s and %s!", from.type.toString(), to.type.toString()));
        if (from.system.equals(to.system)) return d;
        else return to.conversionFactor.apply(d);
    }

    /**
     * Converts a double to another measurement, please note that we do not ensure any checks that the conversion should even be done in the first place, so be careful.
     * @param d the original number to convert
     * @param to the Measurement the double should be converted into
     * @return the converted double
     */
    public static double convert(double d, Measurement to) {
        return to.conversionFactor.apply(d);
    }

    public enum Measurement {
        METRES(System.METRIC, d -> d * 3.28084, Type.LENGTH),
        FEET(System.FREEDOM, d -> d * 0.3048, Type.LENGTH),
        LITRES(System.METRIC, d -> d * 0.264172, Type.VOLUME),
        GALLONS(System.FREEDOM, d -> d * 3.785412, Type.VOLUME),
        GRAMMES(System.METRIC, d -> d * 0.035274, Type.MASS),
        OUNCES(System.FREEDOM, d -> d * 28.34952, Type.MASS),;

        public final System system;
        public final Type type;
        public final UnaryOperator<Double> conversionFactor;

        Measurement(System system, UnaryOperator<Double> conversionFactor, Type type) {
            this.system = system;
            this.conversionFactor = conversionFactor;
            this.type = type;
        }
    }

    public enum Type {
        MASS,
        VOLUME,
        LENGTH
    }

    public enum System {
        METRIC,
        FREEDOM
    }
}
