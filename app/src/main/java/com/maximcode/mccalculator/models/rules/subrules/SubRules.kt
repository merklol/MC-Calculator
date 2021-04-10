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

package com.maximcode.mccalculator.models.rules.subrules

import com.maximcode.core.tables.BinaryOperations
import com.maximcode.core.tables.Functions
import com.maximcode.mccalculator.dto.CalculatorState
import com.maximcode.mccalculator.models.expression.*
import com.maximcode.mccalculator.models.input.*
import com.maximcode.core.tables.Shortcuts
import com.maximcode.core.tables.BaseLexemes

/**
 * Applies when the rest of the sub rules are not applicable.
 */
class Default: SubRule {
    override lateinit var next: SubRule

    override fun apply(state: CalculatorState, value: String) =
        state.copy(expression = state.expression.copyWith(value))
}

/**
 * Applies when the last segment of the expression is a digit and is not
 * the shortcut [Shortcuts.TenWithExponent].
 */
class CurrentInputIsDigit: SubRule {
    override lateinit var next: SubRule

    override fun apply(state: CalculatorState, value: String) =
        when(state.expression.last.isDigit() && state.expression.last.isNotTenWithExponent()) {
            true -> state.copy(expression = state.expression.copyWith(value))
            else -> next.apply(state, value)
        }
}

/**
 * Applies when the segment before the last segment of the expression is:
 *
 * -the binary operation [BinaryOperations.Exponent] and the shortcut [Shortcuts.Squared],
 *
 * -the base lexeme [BaseLexemes.Dot],
 *
 * -the input is the shortcut [Shortcuts.Squared] and equals the base lexeme [BaseLexemes.Dot],
 *
 * -the segment before the last segment of the expression is the shortcut
 * [Shortcuts.TenWithExponent] and the input is the base lexeme [BaseLexemes.Dot].
 */
class CurrentInputIsDot: SubRule {
    override lateinit var next: SubRule

    override fun apply(state: CalculatorState, value: String): CalculatorState {
        val(last, secondLast) = state.expression
        return when(secondLast.isExponent() && value.isSquaredOrDot() || last.isSquared()
                && value.isDot() || secondLast.isTenWithExponent() && value.isDot()) {
            true -> state
            else -> next.apply(state, value)
        }
    }
}

/**
 * Applies when the input is not a digit, or the shortcut is [Shortcuts.DoubleZero]
 * or the shortcut [Shortcuts.TenWithExponent].
 */
class CurrentInputIsNotDigitOrShortcut: SubRule {
    override lateinit var next: SubRule

    override fun apply(state: CalculatorState, value: String) =
        when(value.isNotDigit() || value.isDoubleZero() || value.isTenWithExponent()) {
            true -> state
            else -> next.apply(state, value)
        }
}

/**
 * Applies when the segment before the last segment of the expression is the shortcut
 * [Shortcuts.TenWithExponent] and the input is the shortcut [Shortcuts.Squared].
 */
class CurrentInputIsSquared: SubRule {
    override lateinit var next: SubRule

    override fun apply(state: CalculatorState, value: String) =
        when(state.expression.secondLast.isTenWithExponent() && value.isSquared()) {
            true -> state
            else -> next.apply(state, value)
        }
}

/**
 * Applies when the last segment of the expression is the shortcut
 * [Shortcuts.Squared] and the input is not the shortcut [Shortcuts.Squared].
 */
class PreviousInputIsSquared: SubRule {
    override lateinit var next: SubRule

    override fun apply(state: CalculatorState, value: String) =
        when(state.expression.last.isSquared() && !value.isSquared()) {
            true -> state.copy(expression = state.expression.copyWith(value))
            else -> next.apply(state, value)
        }
}

/**
 * Applies when the last segment of the expression is the base lexeme
 * [BaseLexemes.LeftBracket] and the input is not the shortcut [Shortcuts.TenWithExponent].
 */
class PreviousIsLeftBracket: SubRule {
    override lateinit var next: SubRule

    override fun apply(state: CalculatorState, value: String) =
        when(state.expression.last.hasLeftBracket() && value.isTenWithExponent()) {
            true -> state.copy(expression = state.expression.copyWith(value))
            else -> next.apply(state, value)
        }
}

/**
 * Applies when the input is a new generated random number.
 */
class CurrentInputIsRandomNumber: SubRule {
    override lateinit var next: SubRule
    private val operators = hashSetOf(
        BinaryOperations.Subtract, BinaryOperations.Add, BinaryOperations.Multiply,
        BinaryOperations.Divide, Functions.ExponentialFunction
    )

    override fun apply(state: CalculatorState, value: String) = state.copy(expression = when {
        state.expression.startsWithZero() -> expressionOf(value)
        operators.contains(state.expression.last) -> state.expression.copyWith(value)
        else -> state.expression.copyWith(BinaryOperations.Multiply, value)
    })
}

/**
 * Creates a chain of sub-rules and returns the first applicable one.
 */
fun chainOfSubRules(subRules: List<SubRule>): SubRule {
    subRules.indices.forEach { if(it + 1 < subRules.size) subRules[it].next = subRules[it + 1] }
    return subRules.first()
}