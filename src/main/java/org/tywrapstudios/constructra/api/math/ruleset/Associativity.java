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
