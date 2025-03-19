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

import java.util.*;

import static org.tywrapstudios.constructra.Constructra.LOGGER;
import static org.tywrapstudios.constructra.api.math.ruleset.Associativity.LEFT;
import static org.tywrapstudios.constructra.api.math.ruleset.Associativity.RIGHT;

/**
 * <h1>Infix, The Reverse Polish Notation and The Shunting Yard Algorithm</h1>
 * As one may know, parsing calculations from Strings can be quite the situation.
 * Especially considering different precedences exist per operator, and other types of rules apply too when it comes to the order of writing and evaluation.
 * <p>The way of writing down your equation is called a notation, and different types exist!
 * <p>The mostly used one being infix:
 * <blockquote><pre>
 *     1 + 3 - 4 / 5 * ( 1 + 5 )
 * </pre></blockquote>
 * In this notation, you work from wherever the highest preceding operator starts, which would be something like powers, {@code ^}, all the way down to the one with the lowest, which is even shared between {@code +} and {@code -}, so you have to read those from left to right too.
 * Anything inside parentheses, {@code ()}, is on their turn done before any stuff outside of them, and inside the parentheses the same precedence rules apply all over again.
 * <p>While this is fine for humans, and makes the most sense to us, a computer would have a harder time trying to read from it, having to re-read the equation a lot of times before it can solve it in the proper order.
 * <p>To combat this, there are also notations that do not make use of parentheses logic, can be read 100% from left to right, while preserving your usual precedence rules, and are ideal to parse with Stack based languages.
 * <p>One of these is called postfix, also known as the Reverse Polish Notation, and is what the Shunting Yard Algorithm focuses on.
 * In the following Block Quote, you can see a comparison of the two, using the same equation from earlier:
 * <blockquote><pre>
 *     1 + 3 - 10 / 5 * ( 1 + 5 )
 *     1 3 + 10 5 / 1 5 + * -
 *     // Both read: -8
 * </pre></blockquote>
 * <h2>Postfix</h2>
 * How does it work?
 * For starters, you may have noticed we simply moved the operators to a different place, and the order of the numbers stayed the same.
 * Also note that this does not say "thirteen plus one-hundred-and-five divided by...", but instead says "one three plus ten five divided by..." Be aware of spaces.
 * <p>Another notable difference is the removal of the brackets, which we're allowed to do now because postfix is meant to be read completely left to right, no loose ends, zero tricks.
 * If you start reading, and you encounter an operator, you should look at the {@code 2} numbers ({@code 1} if the operator operates on only a single digit, see {@link Operator#singleOperand}) in front of the operator and evaluate them in the same order.
 * Reading {@code x y +} would for instance be {@code x + y}, and {@code a b ^} would be {@code a ^ b}.
 * <p>Since a single operation can now be "rewritten" into just a single variable of the equation, which we will call "mock numbers" from now on, these {@code x y +} parts are functioning as brackets on their own.
 * This means they can be reused as a number for the next operation:
 * <blockquote><pre>
 *     [1 3 +] [10 5 /] [1 5 +] * -
 *        4      [2       6     *]-
 *       [4             12        -]
 *                   -8
 * </pre></blockquote>
 * (The Square brackets are used here to explain what turns into what, they are not part of the mathematical equation.)
 *
 * <h2>Conversion and Parsing</h2>
 * Converting from infix to postfix needs something that can understand that the order of operations is now enforced by the order of reading, and not which operators are used.
 * It doesn't matter if it's a {@code +} or a {@code *}, as long as you read the {@code +} before the {@code *}, addition will come first.
 * <p>When converting from in- to postfix, try to keep this in mind:
 * <li>Try to keep the order of the numbers themselves as consistent with the infix equation as possible.
 * <li>If you come across an operation that is followed by an operation with a higher precedence, first handle the one with the higher precedence and then figure out where the lower precedent one fits in with it. This may cause you to break rule 1, but that's acceptable.
 * <li>Having loose numbers in front of an operation is not bad as long as there is another operator behind the operation.</li>
 * <p>Lets do another example, feel free to grab a piece of paper to put notes on:
 * <blockquote><pre>
 *     // We start in infix
 *     1 + 2 * (6 - 2)
 *     // Start with the highest precedent part of the equation, in this case [(6 - 2)]
 *     1 + 2 * 6 2 -
 *     // We know this [6 2 -] part needs to be multiplied by [2]
 *     // This means we have to place the * behind the mock number [6 2 -]
 *     1 + 2 6 2 - *
 *     // Now it gets funky, as we place the + all the way at the end of the equation
 *     // We do this because we add [1] to the mock number [2 [6 2 -] *]
 *     1 2 6 2 - * +
 * </pre></blockquote>
 * <h2>Negatives</h2>
 * Negative values are never fun to work with, and that's why it deserves its own chapter.
 * <p>A negative value in infix is simply the integer prefixed with a {@code -}, though in postfix this is slightly different.
 * <p>Lets put two next to each other again:
 * <blockquote><pre>
 *     // We start in infix
 *     -4 * -6 - 4 + -3
 *     // If the infix notation can be shorter, always make it shorter
 *     -4 * -6 - 4 - 3
 *     // Again, start with the highest precedence, in this case [-4 * -6]
 *     -4 -6 * - 4 - 3
 * </pre></blockquote>
 * <p>This isn't really good. From our knowledge, we know that operator symbols should probably be behind the numbers to properly work.
 * Although plain numbers prefixed with a {@code -} are technically still considered integers by the JVM, during parsing and using Stacks it might cause issues due to the parser not being able to differentiate between prefixes and operators.
 * This means that we will have to try to fulfil the act of putting all operators behind the numbers they operate on:
 * <blockquote><pre>
 *     // We put the "operator" - behind the numbers
 *     4 - 6 - * - 4 - 3
 * </pre></blockquote>
 * An operator needs two numbers to work with though, to fix this, we might as well use {@code 0} as the left number.
 * While this might not be 100% correct in terms of notation, when programming using Stacks, replacing a {@code null} or just an empty slot with a {@code 0} is safer and will still work mathematically {@code (0 - x == -x)}.
 * <p>We know a {@code -} symbol is indicating a negative integer if:
 * <li>It's the first symbol in the list of tokens;
 * <li>It has another operator, or an open parenthesis in front of it.</li>
 * <blockquote><pre>
 *     0 4 - 0 6 - * - 4 - 3
 *     // Don't forget the rest of the notation
 *     // [4 - 3] becomes [4 3 -]
 *     // This mock number [4 3 -] is subtracted from the mock number [0 4 - 0 6 - *]
 *     // ( As in: [0 4 - 0 6 - *] - [4 3 -] )
 *     0 4 - 0 6 - * 4 3 - -
 * </pre></blockquote>
 * <h2>The Shunting Yard Algorithm</h2>
 * To see how the algorithm itself works internally, feel free to look at {@link #execute(List)}.
 * If you need an in depth explanation, you can see a good page from Brilliant about it, <a href="https://brilliant.org/wiki/shunting-yard-algorithm/">here</a>
 * @author Tiazzz
 * @see Operator
 * @see StringCalculator#calculate(String calculation)
 * @see #execute(List tokens)
 */
public class ShuntingYard {
    private static final Map<String, Operator> OPS = new HashMap<>();

    static {
        // We build a map with all the existing Operators by iterating over the existing Enum
        // and filling up the map with:
        // <K,V> = <Character, Operator(Character, Associativity, Precedence)>
        for (Operator operator : Operator.values()) {
            OPS.put(operator.symbol, operator);
        }
    }

    /**
     * Parses and returns a List from {@code infix}-notation to {@code postfix}-notation
     * <p>You input a {@link List}{@code <}{@link String}{@code >} that contains every part of the infix notation.
     * <blockquote><pre>
     *     // e.g. "6 + 4 / (6 - 4)"
     *     List<String> tokens = List.of("6", "+", "4", "/", "(", "6", "-", "4", ")");
     * </pre></blockquote>
     * @see StringCalculator#calculate(String)
     * @param tokens a list containing every part of an {@code infix}-notation calculation.
     * @return a list containing every part of a {@code postfix}-notation calculation.
     */
    public static List<String> execute(List<String> tokens) {
        List<String> output = new LinkedList<>();
        Stack<String> stack = new Stack<>();

        // For all the input tokens read the next token
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (OPS.containsKey(token)) {
                // Token is an operator
                Operator currentOp = OPS.get(token);

                // We check if the token is a subtraction token and if it holds either of the following values:
                // It's the first of the tokens
                // There's another operator/open parenthesis in front of it
                if (token.equals(Operator.SUBTRACTION.symbol) && (i == 0 || OPS.containsKey(tokens.get(i-1)) || tokens.get(i-1).equals("("))) {
                    // This is a negative number indicator
                    output.add("0");                // Add 0 as left operand
                    output.add(tokens.get(i+1));    // Add the number
                    output.add("-");                // Add the minus operator
                    i++;                            // Skip the next token since we already processed it
                    continue;
                }

                if (currentOp.singleOperand) {
                    // Single operand operators can be pushed directly since they only need one number
                    stack.push(token);
                    continue;
                }

                while (!stack.isEmpty() && OPS.containsKey(stack.peek())) {
                    // While there is an operator (y) at the top of the operators stack and
                    // either (x) is left-associative and its precedence is less or equal to
                    // that of (y), or (x) is right-associative and its precedence
                    // is less than (y)
                    Operator cOp = OPS.get(token); // Current operator
                    Operator lOp = OPS.get(stack.peek()); // Top operator from the stack
                    if ((cOp.associativity == LEFT && cOp.comparePrecedence(lOp) <= 0) ||
                            (cOp.associativity == RIGHT && cOp.comparePrecedence(lOp) < 0)) {
                        // Pop (y) from the stack
                        // Add (y) output buffer
                        output.add(stack.pop());
                        continue;
                    }
                    break;
                }
                // Push the new operator on the stack
                stack.push(token);
            } else if ("(".equals(token)) {
                // Else If token is left parenthesis, then push it on the stack
                stack.push(token);
            } else if (")".equals(token)) {
                // Else If the token is right parenthesis
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    // Until the top token (from the stack) is left parenthesis, pop from
                    // the stack to the output buffer
                    output.add(stack.pop());
                }
                // Also pop the left parenthesis but don't include it in the output buffer
                stack.pop();
            } else {
                // Else add token to output buffer
                output.add(token);
            }
        }

        while (!stack.isEmpty()) {
            // While there are still operator tokens in the stack, pop them to output S[13]
            output.add(stack.pop());
        }

        return output;
    }

    /**
     * Parses an infix calculation into separate equation parts (tokens).
     * @see #execute(List)
     * @param calculation the calculation to perform this parse on
     * @return all the tokens for an infix calculation
     * @throws InvalidCalculationException if your calculation contains characters that aren't allowed in this implementation
     */
    public static List<String> getInfix(String calculation) throws InvalidCalculationException {
        calculation = calculation
                .replaceAll(" ", "")    // Ensure everything is next to each other
                .replaceAll(",", ".");  // Ensure there are no "," decimal points, as Java will only recognise "."

        Stack<String> cache = new Stack<>();
        List<String> tokens = new ArrayList<>();

        LOGGER.debug("[ShuntingYard => Prerequisites] Checking calculation: " + calculation);

        List<Character> chars = new ArrayList<>();
        for (char c : calculation.toCharArray()) chars.add(c);

        for (int i = 0; i < chars.size(); i++) {
            char c = chars.get(i);
            String s = String.valueOf(c);
            boolean safe = false;
            if (s.matches("\\d|[.]")) {
                cache.push(s);
                safe = true;
                LOGGER.debug("[ShuntingYard => Prerequisites] Pushed to cache: " + s);
            } else if (s.matches("[-+*/%^()]")) {
                String finalizedCachedToken = deCache(cache, tokens, "Found operator, time to add the cache before it.");
                LOGGER.debug("[ShuntingYard => Prerequisites] finalizedCachedToken: " + finalizedCachedToken);
                tokens.add(s);
                safe = true;
                LOGGER.debug("[ShuntingYard => Prerequisites] Add to tokens: " + s);
            }
            for (Operator op : OPS.values()) {
                if (s.matches(String.valueOf(op.symbol.toCharArray()[0]))) {
                    tokens.add(op.symbol);
                    LOGGER.debug("[ShuntingYard => Prerequisites] Add to tokens: " + op.symbol);
                    i = i + op.symbol.length() - 1;
                    safe = true;
                }
            }

            if (!safe) {
                cache.clear();
                throw new InvalidCalculationException("Infix Calculation was not marked safe and probably contained a non-mathematical character: " + s);
            }
        }

        deCache(cache, tokens, "The cache might still have a number as the last Operand, we need to add it.");
        LOGGER.debug("Final infix List: " + tokens);

        return tokens;
    }

    private static String deCache(Stack<String> cache, List<String> tokens, String reason) {
        StringBuilder cachedTokenBuilder = new StringBuilder();
        LOGGER.debug("[ShuntingYard => Cache] De-caching: " + reason);
        for (int i = 0; i < cache.size() + i; i++) {
            cachedTokenBuilder.append(cache.removeFirst());
            LOGGER.debug("[ShuntingYard => Cache] De-cached: " + cachedTokenBuilder);
        }
        String cachedToken = cachedTokenBuilder.toString();
        LOGGER.debug("[ShuntingYard => Cache] Final Token: " + cachedToken);
        if (!cachedToken.isEmpty()) tokens.add(cachedToken);
        else LOGGER.debugWarning("[ShuntingYard => Cache] Empty, skipping.");
        return cachedToken;
    }
}
