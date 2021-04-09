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

package com.maximcode.core.lexer

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class DefaultLexerTest {
    private val lexer = DefaultLexer()

    @Test
    fun `when expression is empty returns an empty list`() {
        assertThat(lexer.tokenize(";"), `is`(emptyList()))
    }

    @Test
    fun `when expression has only numbers returns not empty list`() {
        assertThat(lexer.tokenize("1;"), `is`(not(emptyList())))
    }

    @Test
    fun `when expression is 8 + 5 returns list of right tokens`() {
        assertThat(lexer.tokenize("8+5;"), `is`( listOf("8", "+", "5")))
    }

    @Test
    fun `when expression is 8 + -5 returns list of right tokens`() {
        assertThat(lexer.tokenize("8+-5;"), `is`(listOf("8", "+", "-5")))
    }

    @Test
    fun `when expression is 4 x 2 - 2 returns list of right tokens`() {
        assertThat(lexer.tokenize("4x2-2;"), `is`(listOf("4", "x","2", "-", "2")))
    }

    @Test
    fun `when expression is (8 + -5)^2 + -5 returns list of right tokens`() {
        assertThat(lexer.tokenize("(8+-5)^2+-5;"),
            `is`(listOf("(","8", "+", "-5",")", "^", "2", "+", "-5"))
        )
    }

    @Test
    fun `when expression is sin(10,2 + ((2,21 - 2,1) + 2,1)) returns list of right tokens`() {
        assertThat(lexer.tokenize("sin(10.2+((2.21-2.1)+2.1));"), `is`(listOf(
            "sin", "(", "10.2", "+", "(", "(", "2.21", "-", "2.1", ")", "+", "2.1", ")", ")"))
        )
    }

    @Test
    fun `when expression is (tan(PI + 2)) x e returns list of right tokens`() {
        assertThat(lexer.tokenize("(tan(π+2))xe;"),
            `is`(listOf("(", "tan", "(", "π", "+", "2", ")", ")", "x", "e"))
        )
    }

    @Test
    fun `when expression is 6E3^2 + PI returns list of right tokens`() {
        assertThat(lexer.tokenize("6E3^2+π;"),
            `is`(listOf("6", "E","3", "^", "2", "+", "π",))
        )
    }

    @Test
    fun `when expression is -5 + 2 + -2 +(-5 + 2) + (-3 - 3) x 20 returns list of right tokens`() {
        assertThat(lexer.tokenize("-5+2+-2+(-5+2)+(-3-3)x20;"),
            `is`(listOf("-5", "+","2", "+", "-2", "+", "(", "-5", "+", "2", ")",
                "+", "(", "-3", "-", "3", ")", "x", "20")
            )
        )
    }

    @Test
    fun `when expression is ln(log(2)) returns list of right tokens`() {
        assertThat(lexer.tokenize("ln(log(2));"),
            `is`(listOf("ln", "(","log", "(", "2", ")", ")"))
        )
    }

    @Test
    fun `when expression is cos(5) + acos(5) + asin(5) returns list of right tokens`() {
        assertThat(lexer.tokenize("cos(5)+cos⁻¹(5)+sin⁻¹(5);"), `is`(listOf(
            "cos", "(","5", ")", "+", "cos⁻¹", "(", "5", ")", "+", "sin⁻¹", "(", "5", ")"))
        )
    }

    @Test
    fun `when expression is ans - 6 returns list of right tokens`() {
        assertThat(lexer.tokenize("ans-6;"), `is`(listOf("ans", "-","6")))
    }

    @Test
    fun `when expression is (120 + 6)percent of 100 returns list of right tokens`() {
        assertThat(lexer.tokenize("(120+6)%;"),
            `is`(listOf("(", "120","+", "6", ")", "%"))
        )
    }
}