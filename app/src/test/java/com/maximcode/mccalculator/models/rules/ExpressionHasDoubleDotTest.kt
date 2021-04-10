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

class ExpressionHasDoubleDotTest {
    private val rule = ExpressionHasDoubleDot()

    @Test
    fun `the rule is applicable when last and value are the dot symbol`() {
        val result = rule.isApplicable(
            CalculatorState(expression = expressionOf(".")), ".")
        assertThat(result, `is`(true))
    }

    @Test
    fun `when last and value are the dot symbol returns the same state`() {
        val state = CalculatorState(expression = expressionOf("."))
        val result = rule.apply(state, ".")
        assertThat(result, `is`(state))
    }
}