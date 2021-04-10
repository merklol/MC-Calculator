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

class PreviousInputIsFactorialTest {
    private val rule = PreviousInputIsFactorial()

    @Test
    fun `the rule is applicable when last is factorial and value is factroial`() {
        val result = rule.isApplicable(
            CalculatorState(expression = expressionOf("!")), "!")
        assertThat(result, `is`(true))
    }

    @Test
    fun `when last is left bracket it appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf(")")), "0")
        assertThat(result.expression.toString(), `is`(")0"))
    }

    @Test
    fun `when last is e it appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("e")), "0")
        assertThat(result.expression.toString(), `is`("e0"))
    }

    @Test
    fun `when last is ln2 it appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("ln2")), "0")
        assertThat(result.expression.toString(), `is`("ln20"))
    }

    @Test
    fun `when last is ^2 it appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("^2")), "0")
        assertThat(result.expression.toString(), `is`("^20"))
    }

    @Test
    fun `when last is PI it appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("π")), "0")
        assertThat(result.expression.toString(), `is`("π0"))
    }

    @Test
    fun `when last is ans it appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("ans")), "0")
        assertThat(result.expression.toString(), `is`("ans0"))
    }

    @Test
    fun `when last is a negative digit it appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("-1")), "0")
        assertThat(result.expression.toString(), `is`("-10"))
    }

    @Test
    fun `when last is a positive digit it appends to the expression`() {
        val result = rule.apply(CalculatorState(expression = expressionOf("1")), "0")
        assertThat(result.expression.toString(), `is`("10"))
    }
}