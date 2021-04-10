/*
 * MIT License
 *
 * Copyright (c) 2021 Maxim Smolyakov
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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.maximcode.mccalculator.models.rules

import com.maximcode.core.tables.*
import com.maximcode.mccalculator.dto.CalculatorState
import com.maximcode.mccalculator.models.expression.*
import com.maximcode.mccalculator.models.input.*
import com.maximcode.mccalculator.models.rules.subrules.SubRule
import com.maximcode.mccalculator.models.rules.subrules.chainOfSubRules

/**
 * Applies if the last segment of the expression is the same as the input and equals
 * the base lexeme [BaseLexemes.Dot].
 */
class ExpressionHasDoubleDot: Rule {
    override fun isApplicable(state: CalculatorState, input: String) =
        state.expression.last in input && input == BaseLexemes.Dot

    override fun apply(state: CalculatorState, input: String) = state
}

/**
 * Applies if the expression starts with zero or it is a new expression.
 */
class NewExpression: Rule {
    private val operators = hashSetOf(
        BinaryOperations.Multiply, BinaryOperations.Divide, BinaryOperations.Add,
        Functions.ExponentialFunction, BinaryOperations.Subtract, Functions.Percent,
        Functions.Factorial, Shortcuts.Squared, BinaryOperations.Exponent,
        BaseLexemes.Dot
    )

    override fun isApplicable(state: CalculatorState, input: String) =
        state.expression.startsWithZero() || state.isNewExpression

    override fun apply(state: CalculatorState, input: String) = state.copy(
        expression = when {
            input.isDoubleZero() -> state.expression
            operators.contains(input) -> formatOrElse(state.expression, input) {
                state.expression.copyWith(input)
            }
            else -> expressionOf(input)
        }, isNewExpression = false
    )

    private fun formatOrElse(
        expression: Expression, value: String, defaultValue: () -> Expression) = when {
            expression.isError() || expression.isNaN() ->
            expressionOf("0", value)
        else -> defaultValue()
    }
}

/**
 * Applies if the last segment of the expression is one of the binary operations:
 * [BinaryOperations.Add], [BinaryOperations.Subtract], [BinaryOperations.Divide],
 * [BinaryOperations.Multiply].
 */
class PreviousInputIsBasicOperators: Rule {
    private val operators = hashSetOf(BinaryOperations.Add, BinaryOperations.Subtract,
        BinaryOperations.Divide, BinaryOperations.Multiply
    )

    override fun isApplicable(state: CalculatorState, input: String) =
        operators.contains(state.expression.last) && (input.isSquared() || input.isExponent())

    override fun apply(state: CalculatorState, input: String) = state
}

/**
 * Applies if the last segment of the expression is:
 *
 * the binary operation[BinaryOperations.Exponent],
 *
 * the shortcut [Shortcuts.Squared],
 *
 * the shortcut [Shortcuts.TenWithExponent]
 *
 * the base lexeme [BaseLexemes.LeftBracket] and the binary operation [BinaryOperations.Exponent]
 *
 * the shortcut [Shortcuts.Squared].
 *
 * It also applies if the segment before the last segment of the expression is
 * the binary operation [BinaryOperations.Exponent] or the shortcut [Shortcuts.TenWithExponent].
 */
class PreviousInputIsExponent(private val subRules: List<SubRule>): Rule {
    override fun isApplicable(state: CalculatorState, input: String): Boolean {
        val (last, secondLast) = state.expression

        return last.isExponent() || last.isSquared() || last.isTenWithExponent()
                || last.hasLeftBracket() && input.isExponentOrSquared()
                || secondLast?.isExponent() ?: false
                || secondLast?.isTenWithExponent() ?: false
    }

    override fun apply(state: CalculatorState, input: String) =
        chainOfSubRules(subRules).apply(state, input)
}

/**
 * Applies if the last segment of the expression is not a digit and the input is
 * the function [Functions.Factorial].
 */
class PreviousInputIsFactorial: Rule {
    private val operators = hashSetOf(
        BaseLexemes.RightBracket, Constants.EulerNumber, Constants.LN2,
        Shortcuts.Squared, Constants.PI, Shortcuts.LastAnswer
    )

    override fun isApplicable(state: CalculatorState, input: String) =
        state.expression.last.isNotDigit() && input == Functions.Factorial

    override fun apply(state: CalculatorState, input: String): CalculatorState {
        val last = state.expression.last
        return when {
            operators.contains(last) || last.isPositiveOrNegativeDigit() ->
                state.copy(expression = state.expression.copyWith(input))

            else -> state
        }
    }
}

/**
 * Applies if the last segment of the expression is not a digit and the input is
 * the function [Functions.Percent].
 */
class CurrentInputIsPercent: Rule {
    private val operators = hashSetOf(
        BaseLexemes.RightBracket, Constants.EulerNumber, Constants.LN2,
        Shortcuts.Squared, Constants.PI, Functions.Factorial,
        Shortcuts.LastAnswer
    )

    override fun isApplicable(state: CalculatorState, input: String) =
        state.expression.last.isNotDigit() && input == Functions.Percent

    override fun apply(state: CalculatorState, input: String): CalculatorState {
        val last = state.expression.last
        return when {
            operators.contains(last) || last.isPositiveOrNegativeDigit() ->
                state.copy(expression = state.expression.copyWith(input))

            else -> state
        }
    }
}