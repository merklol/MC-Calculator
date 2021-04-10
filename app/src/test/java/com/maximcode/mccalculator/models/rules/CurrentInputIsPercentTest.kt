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

class CurrentInputIsPercentTest {
    private val rule = CurrentInputIsPercent()

    @Test
    fun `the rule is applicable when last is not digit and value is percent`() {
        val result = rule.isApplicable(
            CalculatorState(expression = expressionOf("sin")), "%")
        assertThat(result, `is`(true))
    }

    @Test
    fun `the rule is not applicable when last is digit and value is percent`() {
        val result = rule.isApplicable(
            CalculatorState(expression = expressionOf("9")), "%")
        assertThat(result, `is`(false))
    }

    @Test
    fun `when last is e and value is percent appends it to the expression`() {
        val result = rule.apply(
            CalculatorState(expression = expressionOf("e")), "%")
        assertThat(result.expression.toString(), `is`("e%"))
    }

    @Test
    fun `when last is ln2 and value is percent appends it to the expression`() {
        val result = rule.apply(
            CalculatorState(expression = expressionOf("ln2")), "%")
        assertThat(result.expression.toString(), `is`("ln2%"))
    }

    @Test
    fun `when last is ^2 and value is percent append it to the expression`() {
        val result = rule.apply(
            CalculatorState(expression = expressionOf("^2")), "%")
        assertThat(result.expression.toString(), `is`("^2%"))
    }

    @Test
    fun `when last is PI and value is percent appends it to the expression`() {
        val result = rule.apply(
            CalculatorState(expression = expressionOf("π")), "%")
        assertThat(result.expression.toString(), `is`("π%"))
    }

    @Test
    fun `when last is ! and value is percent appends it to the expression`() {
        val result = rule.apply(
            CalculatorState(expression = expressionOf("!")), "%")
        assertThat(result.expression.toString(), `is`("!%"))
    }

    @Test
    fun `when last is and and value is percent appends it to the expression`() {
        val result = rule.apply(
            CalculatorState(expression = expressionOf("ans")), "%")
        assertThat(result.expression.toString(), `is`("ans%"))
    }

    @Test
    fun `when last is a negative digit and value is percent appends it to the expression`() {
        val result = rule.apply(
            CalculatorState(expression = expressionOf("-1")), "%")
        assertThat(result.expression.toString(), `is`("-1%"))
    }

    @Test
    fun `when last is a digit and value is percent appends it to the expression`() {
        val result = rule.apply(
            CalculatorState(expression = expressionOf("1")), "%")
        assertThat(result.expression.toString(), `is`("1%"))
    }

    @Test
    fun `when last is percent and value is percent it returns the same state`() {
        val state = CalculatorState(expression = expressionOf("%"))
        val result = rule.apply(state, "%")
        assertThat(result, `is`(state))
    }
}