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

import com.maximcode.mccalculator.dto.CalculatorState
import com.maximcode.mccalculator.models.expression.expressionOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class NewExpressionTest {
    private val rule = NewExpression()

    @Test
    fun `when it's a new expression or starts with 0 the x symbol  appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("0")), "x")
        assertThat(result.expression.toString(), `is`("0x"))
    }

    @Test
    fun `when it's a new expression or starts with 0 the divide symbol appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("0")), "÷")
        assertThat(result.expression.toString(), `is`("0÷"))
    }

    @Test
    fun `when it's a new expression or starts with 0 the + symbol appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("0")), "+")
        assertThat(result.expression.toString(), `is`("0+"))
    }

    @Test
    fun `when it's a new expression or starts with 0 the E symbol appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("0")), "E")
        assertThat(result.expression.toString(), `is`("0E"))
    }

    @Test
    fun `when it's a new expression or starts with 0 the - symbol appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("0")), "-")
        assertThat(result.expression.toString(), `is`("0-"))
    }

    @Test
    fun `when it's a new expression or starts with 0 the percent symbol appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("0")), "%")
        assertThat(result.expression.toString(), `is`("0%"))
    }

    @Test
    fun `when it's a new expression or starts with 0 the ! symbol appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("0")), "!")
        assertThat(result.expression.toString(), `is`("0!"))
    }

    @Test
    fun `when it's a new expression or starts with 0 the ^2 symbol appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("0")), "^2")
        assertThat(result.expression.toString(), `is`("0^2"))
    }

    @Test
    fun `when it's a new expression or starts with 0 the ^ symbol appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("0")), "^")
        assertThat(result.expression.toString(), `is`("0^"))
    }

    @Test
    fun `when it's a new expression or starts with 0 the dot symbol appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("0")), ".")
        assertThat(result.expression.toString(), `is`("0."))
    }

    @Test
    fun `when it's a new expression or starts with 0 and value sin replaces 0 with sin`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("0")), "sin")
        assertThat(result.expression.toString(), `is`("sin"))
    }

    @Test
    fun `when it's not a new expression and value is 00 returns the same state`() {
        val state = CalculatorState(expression = expressionOf("0"), isNewExpression = false)
        val result = rule.apply(state, "00")
        assertThat(result, `is`(state))
    }

    @Test
    fun `the rule isn't applicable when it's not a new expression and value doesn't start with 0`() {
        val state = CalculatorState(expression = expressionOf("1"), isNewExpression = false)
        val result = rule.isApplicable(state, "0")
        assertThat(result, `is`(false))
    }

    @Test
    fun `the rule is applicable when it's not a new expression and value starts with 0`() {
        val state = CalculatorState(expression = expressionOf("0"), isNewExpression = false)
        val result = rule.isApplicable(state, "0")
        assertThat(result, `is`(true))
    }

    @Test
    fun `the rule is applicable when it's a new expression and value starts with 0`() {
        val state = CalculatorState(expression = expressionOf("0"), isNewExpression = true)
        val result = rule.isApplicable(state, "0")
        assertThat(result, `is`(true))
    }
}