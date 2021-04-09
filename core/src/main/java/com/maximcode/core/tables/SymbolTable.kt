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

package com.maximcode.core.tables

const val Error = "Error"
const val NaN = "NaN"

object BaseLexemes {
    const val LeftBracket = "("
    const val RightBracket = ")"
    const val Dot = "."
}

object Constants {
    const val PI = "π"
    const val LN2 = "ln2"
    const val EulerNumber = "e"
}

object Shortcuts {
    const val LastAnswer = "ans"
    const val Squared = "^2"
    const val TenWithExponent = "10^"
    const val DoubleZero = "00"
}

object BinaryOperations {
    const val Add ="+"
    const val Subtract = "-"
    const val Multiply = "x"
    const val Divide = "÷"
    const val Exponent = "^"
}

object Functions {
    const val Percent = "%"
    const val Factorial = "!"
    const val SquareRoot = "√"
    const val Logarithm = "log"
    const val NLogarithm = "ln"
    const val ExponentialFunction ="E"
}

object TrigonometricFunctions {
    const val Sine = "sin"
    const val Cosine = "cos"
    const val ArcSine = "sin⁻¹"
    const val ArcCosine = "cos⁻¹"
    const val Tangent = "tan"
    const val ArcTangent = "tan⁻¹"
}

val baseLanguage = arrayOf('+', '-', 'x', '÷', '^', '!', '√', '(', ')', ';', 'E', '%')