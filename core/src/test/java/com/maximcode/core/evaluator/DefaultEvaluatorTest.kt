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

package com.maximcode.core.evaluator

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class DefaultEvaluatorTest {

    @Test
    fun `when expression is 2 + 5 returns the right answer`() {
        val evaluator = DefaultEvaluator()
        assertThat(evaluator.evaluateExpression("2+5"), `is`(7))
    }

    @Test
    fun `when expression is 2 + 5 - 10 returns the right answer`() {
        val evaluator = DefaultEvaluator()
        assertThat(evaluator.evaluateExpression("2+5-10"), `is`(-3))
    }

    @Test
    fun `when expression is 2 + 3 + 10 - 50 returns the right answer`() {
        val evaluator = DefaultEvaluator()
        assertThat(evaluator.evaluateExpression("2+3+10-50"), `is`(-35))
    }

    @Test
    fun `when expression is 2 + -3 + 10 + 25 returns the right answer`() {
        val evaluator = DefaultEvaluator()
        assertThat(evaluator.evaluateExpression("2+-3+10+25"), `is`(34))
    }

    @Test
    fun `when expression is (8 + -5)^2 + -5 returns the right answer`() {
        val evaluator = DefaultEvaluator()
        assertThat(evaluator.evaluateExpression("(8+-5)^2+-5"), `is`(4))
    }

    @Test
    fun `when expression is (120 + 6) percents of 100 return the right answer`() {
        val evaluator = DefaultEvaluator()
        assertThat(evaluator.evaluateExpression("(120+6)%"), `is`(1.26))
    }

    @Test
    fun `when expression is sin(0) + cos(0) + tan(0) + 1 returns the right answer`() {
        val evaluator = DefaultEvaluator()
        assertThat(evaluator.evaluateExpression("sin(0)+cos(0)+tan(0)+1"), `is`(2))
    }

    @Test
    fun `when expression is invalid returns the smallest positive nonzero value`() {
        val evaluator = DefaultEvaluator()
        assertThat(evaluator.evaluateExpression("0 -+"), `is`(Double.MIN_VALUE))
    }
}