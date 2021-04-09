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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.maximcode.core.parser

import com.maximcode.core.lexer.DefaultLexer
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class DefaultParserTest {
    private val parser = DefaultParser(lexer = DefaultLexer())

    @Test
    fun `when expression is 2 returns list of right nodes in the right order`() {
        assertThat(parser.buildAST("2;"), `is`(listOf(Any("2"))))
    }

    @Test
    fun `when expression is 2 + 3 returns list of right nodes in the right order`() {
        assertThat(parser.buildAST("2+3;"),
            `is`(listOf(Any("2"), Any("3"), Add("+")))
        )
    }

    @Test
    fun `when expression is 2 + -3 returns list of right nodes in the right order`() {
        assertThat(parser.buildAST("2+-3;"),
            `is`(listOf(Any("2"), Any("-3"), Add("+")))
        )
    }

    @Test
    fun `when expression is (8 + -5)^2 + -5 returns list of right nodes in the right order`() {
        assertThat(parser.buildAST("(8+-5)^2+-5;"), `is`(
            listOf(Any("8"), Any("-5"), Add("+"),
                Any("2"),Exponent("^"), Any("-5"),Add("+"))
        ))
    }

    @Test
    fun `when expression is sin(10,2 + ((2,21 - 2,1) + 2,1)) returns list of right nodes in the right order`() {
        assertThat(parser.buildAST("sin(10.2+((2.21-2.1)+2.1));"), `is`(
            listOf(Any("10.2"), Any("2.21"), Any("2.1"), Subtract("-"),
                Any("2.1"), Add("+"), Add("+"), Sine("sin")))
        )
    }

    @Test
    fun `when expression is (tan(PI + 2)) x e returns list of right nodes in the right order`() {
        assertThat(parser.buildAST("(tan(π+2))xe;"), `is`(
            listOf(Any("π"), Any("2"), Add("+"), Tangent("tan"),
                Any("e"), Multiply("x")))
        )
    }

    @Test
    fun `when expression is 6E3^2 + PI returns list of right nodes in the right order`() {
        assertThat(parser.buildAST("6E3^2+π;"), `is`(
            listOf(Any("6"), Any("3"), ExponentialFunction("E"), Any("2"),
                Exponent("^"), Any("π"), Add("+")))
        )
    }

    @Test
    fun `when expression is cos(5) + acos(5) + asin(5) returns list of right nodes in the right order`() {
        assertThat(parser.buildAST("cos(5)+cos⁻¹(5)+sin⁻¹(5);"), `is`(
            listOf(Any("5"), Cosine("cos"), Any("5"), ArcCosine("cos⁻¹"),
                Add("+"), Any("5"), ArcSine("sin⁻¹"), Add("+")))
        )
    }

    @Test
    fun `when expression is ans - 6 returns list of right nodes in the right order`() {
        assertThat(parser.buildAST("ans-6;"), `is`(
            listOf(LastAnswer("ans"), Any("6"), Subtract("-")))
        )
    }

    @Test
    fun `when expression is (120 + 6) percent of 100 returns list of right nodes in the right order`() {
        assertThat(parser.buildAST("(120+6)%;"), `is`(
            listOf(Any("120"), Any("6"), Add("+"), Percent("%")))
        )
    }
}