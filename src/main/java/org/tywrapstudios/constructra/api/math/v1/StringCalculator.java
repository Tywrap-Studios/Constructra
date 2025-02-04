package org.tywrapstudios.constructra.api.math.v1;

import java.util.*;

import static java.lang.Double.NaN;
import static org.tywrapstudios.constructra.Constructra.LOGGER;
import static java.lang.Math.*;

/**
 * A class that can handle calculations directly from Strings.
 * <p>Due to this class utilizing a lot of logic from other classes, which have better and extensive documentation, we are not going to go over all the details here.
 * <p>In this class there's the {@link #calculate(String)} method which can be run on a string with an expression. View its JavaDoc for more info.</p>
 * @see Operator Operator - info about Operations
 * @see ShuntingYard ShuntingYard - info about the Shunting Yard Algorithm and in-/postfix
 * @see CalculationBuilder CalculationBuilder - the thing that actually does calculations
 * @see Associativity Associativity - info about how Associativity affects Operations and in-/postfix parsing
 */
public class StringCalculator {
    /**
     * Returns a double, which is the result of the expression you provide.
     * <p>All parts of this method can be ran separately if needed, and may be of use to you in other contexts.</p>
     * <p>For all allowed Operations please consult {@link Operator}</p>
     * @param calculation the calculation in form of a String
     * @return the result of the provided calculation
     */
    public static double calculate(String calculation) throws InvalidCalculationException {
        CalculationBuilder builder = new CalculationBuilder();
        if (calculation == null || calculation.isEmpty()) return NaN;

        List<String> tokens = ShuntingYard.getInfix(calculation);
        LOGGER.debug("[StringCalculator] Tokens: " + tokens);
        List<String> postfixTokens = ShuntingYard.execute(tokens);
        LOGGER.debug("[StringCalculator] Postfix: " + postfixTokens);

        return builder.fromPostfix(postfixTokens).build();
    }

    public static String calculateStr(String calculation) {
        double d;
        try {
            d = calculate(calculation);
        } catch (Exception e) {
            return calculation;
        }
        if (Double.isNaN(d)) return calculation;
        else return Double.toString(d);
    }

    public static class CalculationBuilder {
        private static String calcString;
        private static StringBuilder calcStringBuilder;
        private static double calcOutCome;
        private final static Map<String, Operator> OPS = new HashMap<>();

        public CalculationBuilder() {
            calcStringBuilder = new StringBuilder();
            calcStringBuilder.append("0");
            calcOutCome = 0.0;
        }

        /**
         * Handles, parses and calculates operations from a Postfix Token list, and adds it to your total calculation.
         * @see ShuntingYard#execute(List)
         * @see ShuntingYard#getInfix(String)
         * @param postfix The List of Tokens from your Postfix Notated expression
         * @return this builder
         */
        public CalculationBuilder fromPostfix(List<String> postfix) {
            // We make a new stack to store the outputs in
            Stack<String> N = new Stack<>();
            for (String s : postfix) {
                LOGGER.debug("[StringCalculator$CalculationBuilder] Checking Token: " + s);
                // If the string is not an operator, we push it to the numbers stack
                if (!OPS.containsKey(s)) {
                    N.push(s);
                    LOGGER.debug("[StringCalculator$CalculationBuilder] Pushed: " + s);
                } else {
                    // If it is, we get it from the OPS list
                    Operator op = OPS.get(s);
                    // We get the right associative digit, by popping it
                    double right = Double.parseDouble(N.pop());
                    // We try to get the left associative digit too, by popping it.
                    // If it doesn't exist if the operator operates on a single operand
                    // we can always assign it to right here if the check is false,
                    // as Single Operand Operators have Right Associativity
                    double left = !op.singleOperand ? Double.parseDouble(N.pop()) : right;
                    // We calculate and push the outcome
                    double newD = getFromOperation(op, left, right);
                    LOGGER.debug("[StringCalculator$CalculationBuilder] newD: " + newD);
                    N.push(String.valueOf(newD));
                }
            }
            // Finally, we add the outcome of the postfix equation to the builder's total outcome.
            add(Double.parseDouble(N.pop()));
            return this;
        }

        private double getFromOperation(Operator op, double d1, double d2) {
            return switch (op) {
                case ADDITION -> d1 + d2;
                case SUBTRACTION -> d1 - d2;
                case MULTIPLICATION -> d1 * d2;
                case DIVISION -> d1 / d2;
                case MODULUS -> d1 % d2;
                case POWER -> pow(d1, d2);
                case SQRT -> sqrt(d1);
                case CEIL -> ceil(d1);
                case FLOOR -> floor(d1);
                case ROUND -> round(d1);
            };
        }

        /**
         * Runs the applicable method for the input Operator.
         * @param op the Operation to perform
         * @param d1 the double that will be used for the Operation
         * @return this builder
         */
        public CalculationBuilder doOperation(Operator op, double d1, double d2) {
            return switch (op) {
                case ADDITION -> this.add(d1);
                case SUBTRACTION -> this.subtract(d1);
                default -> this.add(getFromOperation(op, d1, d2));
            };
        }

        /**
         * Adds the doubles you provide to the total calculation.
         * @param d1 the doubles to add
         * @return this builder
         */
        public CalculationBuilder add(double... d1) {
            for (double d : d1) {
                calcOutCome = calcOutCome + d;
                calcStringBuilder.append(String.format(" + %s", d));
                LOGGER.debug("[StringCalculator$CalculationBuilder] Addition completed: " + calcOutCome + " " + calcStringBuilder.toString());
            }
            return this;
        }

        /**
         * Subtracts the doubles you provide from the total calculation.
         * @param d1 the doubles to subtract with
         * @return this builder
         */
        public CalculationBuilder subtract(double... d1) {
            for (double d : d1) {
                calcOutCome = calcOutCome - d;
                calcStringBuilder.append(String.format(" - %s", d));
                LOGGER.debug("[StringCalculator$CalculationBuilder] Subtraction completed: " + calcOutCome + " " + calcStringBuilder.toString());
            }
            return this;
        }

        /**
         * Builds and finalizes the builder.
         * @return the outcome of the calculation
         */
        public double build() {
            calcString = calcStringBuilder.toString();
            LOGGER.debug("[StringCalculator$CalculationBuilder] Finalized Builder with final outcome of: " + calcOutCome);
            LOGGER.debug("[StringCalculator$CalculationBuilder] Calculation: " + calcString);
            return calcOutCome;
        }

        /**
         * @return the String that sums up the calculations done
         */
        public String getCalculationString() {
            return calcString;
        }

        static {
            for (Operator operator : Operator.values()) {
                OPS.put(operator.symbol, operator);
            }
        }
    }
}
