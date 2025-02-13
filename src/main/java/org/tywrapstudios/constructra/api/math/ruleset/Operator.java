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
