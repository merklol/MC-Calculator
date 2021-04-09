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

package com.maximcode.core

import com.maximcode.core.parser.*
import com.maximcode.core.tables.BinaryOperations
import com.maximcode.core.tables.Functions
import com.maximcode.core.tables.Shortcuts
import com.maximcode.core.tables.TrigonometricFunctions

/**
Lexical Token.
 */
typealias Token = String

/**
 * Converts this Token value to Node.
 * @throws IllegalArgumentException to indicate that this Token is illegal or inappropriate.
 * @return [Node]
 */
fun Token.toNode() = when (this) {
    BinaryOperations.Add -> Add(this)
    BinaryOperations.Subtract -> Subtract(this)
    BinaryOperations.Multiply -> Multiply(this)
    BinaryOperations.Divide -> Divide(this)
    BinaryOperations.Exponent -> Exponent(this)

    Functions.Percent -> Percent(this)
    Functions.ExponentialFunction -> ExponentialFunction(this)
    Functions.Factorial -> Factorial(this)
    Functions.SquareRoot -> SquareRoot(this)
    Functions.Logarithm -> Logarithm(this)
    Functions.NLogarithm -> NLogarithm(this)

    TrigonometricFunctions.Tangent -> Tangent(this)
    TrigonometricFunctions.ArcTangent -> ArcTangent(this)
    TrigonometricFunctions.Sine -> Sine(this)
    TrigonometricFunctions.ArcSine -> ArcSine(this)
    TrigonometricFunctions.Cosine -> Cosine(this)
    TrigonometricFunctions.ArcCosine -> ArcCosine(this)

    Shortcuts.LastAnswer -> LastAnswer(this)
    else -> throw IllegalArgumentException("Invalid token.")
}