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

import kotlin.Any

/**
 * Base node type.
 */
sealed class Node(val value: String) {
    override fun equals(other: Any?) =  other is Node && other.value == value

    override fun hashCode() = value.hashCode()
}

/**
 * Any node type represents constants, brackets and numeric values.
 */
class Any(value: String): Node(value)

/**
 * Add node type represents a binary operation.
 */
class Add(value: String): Node(value)

/**
 * Subtract node type represents a binary operation.
 */
class Subtract(value: String): Node(value)

/**
 * Multiply node type represents a binary operation.
 */
class Multiply(value: String): Node(value)

/**
 * Divide node type represents a binary operation.
 */
class Divide(value: String): Node(value)

/**
 * Percentage node type represents a function.
 */
class Percent(value: String): Node(value)

/**
 * ExponentialFunction node type represents a function.
 */
class ExponentialFunction(value: String): Node(value)

/**
 * Factorial node type represents a function.
 */
class Factorial(value: String): Node(value)

/**
 * SquareRoot node type represents a function.
 */
class SquareRoot(value: String): Node(value)

/**
 * Exponent node type represents a binary operation.
 */
class Exponent(value: String): Node(value)

/**
 * Sine node type represents a trigonometric function.
 */
class Sine(value: String): Node(value)

/**
 * ArcSine node type represents a trigonometric function.
 */
class ArcSine(value: String): Node(value)

/**
 * Cosine node type represents a trigonometric function.
 */
class Cosine(value: String): Node(value)

/**
 * ArcCosine node type represents a trigonometric function.
 */
class ArcCosine(value: String): Node(value)

/**
 * Tangent node type represents a trigonometric function.
 */
class Tangent(value: String): Node(value)

/**
 * ArcTangent node type represents a trigonometric function.
 */
class ArcTangent(value: String): Node(value)

/**
 * Logarithm node type represents a function.
 */
class Logarithm(value: String): Node(value)

/**
 * NLogarithm node type represents a function.
 */
class NLogarithm(value: String): Node(value)

/**
 * LastAnswer node type represents a shortcut.
 */
class LastAnswer(value: String): Node(value)

/**
 * LastAnswer node type represents a constant.
 */
class Pi(value: String): Node(value)

/**
 * LastAnswer node type represents a constant.
 */
class EulerNumber(value: String): Node(value)

/**
 * NaturalLog2 node type represents a function.
 */
class NaturalLog2(value: String): Node(value)