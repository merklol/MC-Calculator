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

package com.maximcode.mccalculator.models.expression

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class DefaultExpressionTest {

    @Test
    fun `when expression is cos(5)+acos(5)+asin(5) toFormattedString format it correctly`() {
        val expression = DefaultExpression(
            listOf("cos(", "5",")","+", "cos⁻¹(", "5", ")", "+", "sin⁻¹(", "5", ")")
        )
        assertThat(expression.toFormattedString(), `is`("cos(5) + cos⁻¹(5) + sin⁻¹(5)"))
    }

    @Test
    fun `when expression is (tan(PI+2))xe toFormattedString format it correctly`() {
        val expression = DefaultExpression(listOf("(", "tan(","π","+", "2", ")", ")", "x", "e")
        )
        assertThat(expression.toFormattedString(), `is`("(tan(π + 2)) x e"))
    }

    @Test
    fun `when expression is sin(10,2+((2,21-2,1)+2,1)) toFormattedString format it correctly`() {
        val expression = DefaultExpression(
            listOf("sin(", "10.2","+","(", "(", "2.21", "-", "2.1", ")", "+", "2.1", ")", ")" )
        )
        assertThat(expression.toFormattedString(), `is`("sin(10.2 + ((2.21 - 2.1) + 2.1))"))
    }
}