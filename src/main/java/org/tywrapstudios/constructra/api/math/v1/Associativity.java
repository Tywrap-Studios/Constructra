package org.tywrapstudios.constructra.api.math.v1;

/**
 * <h1>Associativity</h1>
 * The associativity of an operator is a property that determines how operators of the same precedence are grouped in the absence of parentheses.
 * Thus, the choice of which operations to apply the operand to, is determined by the associativity of the operators.
 * <p>Operators may be:
 * <li>left-associative (meaning the operations are grouped from the left);
 * <li>right-associative (meaning the operations are grouped from the right);</li>
 * <h3>Example</h3>
 * <p>Consider the expression {@code a ~ b ~ c}. If the operator {@code ~} has left associativity, this expression would be interpreted as {@code (a ~ b) ~ c}.
 * If the operator has right associativity, the expression would be interpreted as {@code a ~ (b ~ c)}.
 * <p>If the operator is non-associative, the expression might be a syntax error, or it might have some special meaning.
 * <h3>Note</h3>
 * <p>Some mathematical operators have inherent associativity.
 * For example, subtraction and division, as used in conventional math notation, are inherently left-associative.
 * Addition and multiplication, by contrast, are both left and right associative. (e.g. {@code (a * b) * c = a * (b * c)}).</p>
 * @see Operator
 * @author <a href="https://en.wikipedia.org/wiki/Operator_associativity#Non-associative_operators">From Wikipedia, the free encyclopedia</a>
 */
public enum Associativity {
    LEFT,
    RIGHT,
}
