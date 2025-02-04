package org.tywrapstudios.constructra.api.math.v1;

/**
 * Because the world has differences and people can't make up their mind, different unit systems exist.
 * For the sake of these differences, have a class that has them and can convert from and to them.
 */
public class Units {

    /**
     * Converts a double from one Measurement to another
     * @param d the original number to convert
     * @param from the Measurement that the double is in
     * @param to the Measurement the double should be converted into
     * @return the converted double
     * @throws InvalidCalculationException if something goes wrong
     */
    public static double convert(double d, Measurement from, Measurement to) throws InvalidCalculationException {
        if (from.type != to.type) throw new InvalidCalculationException(String.format("Tried to convert units of inconvertible types %s and %s!", from.type.toString(), to.type.toString()));
        if (from.system.equals(to.system)) return d;
        else return d * to.conversionFactor;
    }

    public enum Measurement {
        METERS(System.METRIC, 3.28084, Type.LENGTH),
        FEET(System.FREEDOM, 0.3048, Type.LENGTH),
        LITRES(System.METRIC, 0.264172, Type.VOLUME),
        GALLONS(System.FREEDOM,3.785412, Type.VOLUME),
        GRAMS(System.METRIC, 0.035274, Type.MASS),
        OUNCES(System.FREEDOM, 28.34952, Type.MASS),;

        public final System system;
        public final Type type;
        public final double conversionFactor;

        Measurement(System system, double conversionFactor, Type type) {
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
