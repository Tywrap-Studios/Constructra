package org.tywrapstudios.constructra.api.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.lang.Math.*;

public class ShuntingYardTest {
    @Test
    public void testShuntingYard() {
        String calculation = "1+2*3+sqrt(76+(87+5))";
        double expected = 1+2*3+sqrt(76+(87+5));
        double result = 0;
        try {
            result = StringCalculator.calculate(calculation);
        } catch (Exception ignored) {}

        Assertions.assertEquals(expected, result);
    }
}
