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

package org.tywrapstudios.constructra.api.math;

import org.tywrapstudios.constructra.api.math.exception.InvalidCalculationException;
import org.tywrapstudios.constructra.api.math.ruleset.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static java.lang.Double.NaN;
import static java.lang.Math.*;
import static org.tywrapstudios.constructra.Constructra.LOGGER;

/**
 * A class that can handle calculations directly from Strings.
 * <p>Due to this class utilizing a lot of logic from other classes, which have better and extensive documentation, we are not going to go over all the details here.
 * <p>In this class there's the {@link #calculate(String)} method which can be run on a string with an expression. View its JavaDoc for more info.</p>
 * @see ShuntingYard
 * @see StringCalculator.CalculationBuilder#fromPostfix(List) 
 */
public class StringCalculator {
    
    /**
     * Returns a double, which is the result of the expression you provide.
     * <p>All parts of this method can be ran separately if needed, and may be of use to you in other contexts.</p>
     * <p>For all allowed Operations please consult {@link Operator}</p>
     * @param calculation the calculation in form of a String
     * @return the result of the provided calculation
     * @throws InvalidCalculationException if the calculation is invalid or wrong.
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

    /**
     * Runs a calculation using {@linkplain #calculate(String)} safely.
     * @param calculation the calculation in String form.
     * @return either the result or the input, depending on whether something went wrong.
     */
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
            Stack<Double> N = new Stack<>();
            for (String s : postfix) {
                LOGGER.debug("[StringCalculator$CalculationBuilder] Checking Token: " + s);
                // If the string is not an operator, we push it to the numbers stack
                if (!OPS.containsKey(s)) {
                    N.push(Double.parseDouble(s));
                    LOGGER.debug("[StringCalculator$CalculationBuilder] Pushed: " + s);
                } else {
                    // If it is, we get it from the OPS list
                    Operator op = OPS.get(s);
                    // We get the right associative digit, by popping it
                    double right = N.pop();
                    // We try to get the left associative digit too, by popping it.
                    // If it doesn't exist if the operator operates on a single operand
                    // we can always assign it to right here if the check is false,
                    // as Single Operand Operators have Right Associativity
                    double left = !op.singleOperand ? N.pop() : right;
                    // We calculate and push the outcome
                    double result = getFromOperation(op, left, right);
                    LOGGER.debug("[StringCalculator$CalculationBuilder] result: " + result);
                    N.push(result);
                }
            }
            // Finally, we add the outcome of the postfix equation to the builder's total outcome.
            add(N.pop());
            return this;
        }

        /**
         * Calculate the result of an operation with one or two operands.
         * @implNote If the operator is a single digit operand, we are guaranteed to use the d1 value.
         * @param op the operator to use
         * @param d1 the first, left digit of the operation
         * @param d2 the second, right digit of the operation
         * @return the result of the operation
         */
        private double getFromOperation(Operator op, double d1, double d2) {
            return op.operation.operate(d1, d2);
        }

        /**
         * Runs the applicable method for the input Operator.
         * @param op the Operation to perform
         * @param d1 the double that will be used for the Operation
         * @return this builder
         */
        public CalculationBuilder doOperation(Operator op, double d1, double d2) {
            this.add(op.operation.operate(d1, d2));
            return this;
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
