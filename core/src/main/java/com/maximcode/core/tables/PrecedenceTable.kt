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

package com.maximcode.core.tables

/**
 * Precedence table determines how operators and functions are parsed concerning each other.
 * Operators and functions with higher precedence become the operands of operators with
 * lower precedence.
 */
val precedenceTable = hashMapOf(
    BinaryOperations.Add to 0,
    BinaryOperations.Subtract to 0,
    BinaryOperations.Multiply to 1,
    BinaryOperations.Divide to 1,
    Functions.Percent to 2,
    Functions.ExponentialFunction to 2,
    Functions.Factorial to 2,
    Functions.SquareRoot to 2,
    Functions.Logarithm to 2,
    Functions.NLogarithm to 2,
    Shortcuts.LastAnswer to 2,
    BinaryOperations.Exponent to 2,
    TrigonometricFunctions.Sine to 2,
    TrigonometricFunctions.ArcSine to 2,
    TrigonometricFunctions.Cosine to 2,
    TrigonometricFunctions.ArcCosine to 2,
    TrigonometricFunctions.Tangent to 2,
    TrigonometricFunctions.ArcTangent to 2
)