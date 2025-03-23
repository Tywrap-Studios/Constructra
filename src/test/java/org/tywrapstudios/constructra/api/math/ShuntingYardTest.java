package org.tywrapstudios.constructra.api.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tywrapstudios.constructra.api.math.exception.InvalidCalculationException;

import static java.lang.Math.*;

public class ShuntingYardTest {
    @Test
    public void testShuntingYard() throws InvalidCalculationException {
        String calculation = "floor(1+2*3+sqrt(76+(87+5)))";
        double expected = floor(1+2*3+sqrt(76+(87+5)));
        double result = StringCalculator.calculate(calculation);

        Assertions.assertEquals(expected, result);
    }
}
