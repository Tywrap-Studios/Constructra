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

/**
 * <h1>Operators</h1>
 * Operators are used to perform operations, more often called calculations, on a set of numbers.
 * <p>They can have different precedences and should be handled accordingly inside an equation, as indicated by PEMDAS.</p>
 * <h3>PEMDAS</h3>
 * PEMDAS is an acronym for the words parenthesis, exponents, multiplication, division, addition, subtraction.
 * Given two or more operations in a single expression, the order of the letters in PEMDAS tells you what to calculate first, second, third and so on, until the calculation is complete.
 * <p>This is important to contain consistency with similar equations, that have a different order in which the numbers were placed.
 * (Do note {@link Associativity} applies)</p>
 */
public enum Operator implements Comparable<Operator> {
    ADDITION("+", Associativity.LEFT, 0),
    SUBTRACTION("-", Associativity.LEFT, 0),
    DIVISION("/", Associativity.LEFT, 5),
    MULTIPLICATION("*", Associativity.LEFT, 5),
    MODULUS("%", Associativity.LEFT, 5),
    POWER("^", Associativity.RIGHT, 10),
    SQRT("sqrt", Associativity.RIGHT, 10, true),
    CEIL("ceil", Associativity.RIGHT, 10, true),
    FLOOR("floor", Associativity.RIGHT, 10, true),
    ROUND("round", Associativity.RIGHT, 10, true),;

    public final Associativity associativity;
    public final int precedence;
    public final String symbol;
    public final boolean singleOperand;

    Operator(String symbol, Associativity associativity, int precedence, boolean singleOperand) {
        this.symbol = symbol;
        this.associativity = associativity;
        this.precedence = precedence;
        this.singleOperand = singleOperand;
    }

    Operator(String symbol, Associativity associativity, int precedence) {
        this(symbol, associativity, precedence, false);
    }

    public int comparePrecedence(Operator operator) {
        return this.precedence - operator.precedence;
    }
}
