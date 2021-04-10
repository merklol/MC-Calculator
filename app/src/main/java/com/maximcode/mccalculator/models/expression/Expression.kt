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

/**
 * Mathematical expression.
 */
interface Expression {
    /**
     * Returns the length of the expression.
     */
    val length: Int

    /**
     * Returns the last entered segment.
     */
    val last: String

    /**
     * Returns the segment before the last entered segment or null if the expression
     * is shorter than 2.
     */
    val secondLast: String?

    /**
     * Returns the last segment of the expression for a destructuring declaration.
     */
    operator fun component1(): String

    /**
     * Returns the segment before the last entered segment of the expression
     * for a destructuring declaration.
     */
    operator fun component2(): String?

    /**
     * Returns true if the expression is NaN.
     */
    fun isNaN(): Boolean

    /**
     * Returns true if the expression is wrong.
     */
    fun isError(): Boolean

    /**
     * Returns true if the expression is not zero.
     */
    fun isNotZero(): Boolean

    /**
     * Converts the expression to a formatted string.
     */
    fun toFormattedString(): String

    /**
     * Deletes the last segment of the expression.
     */
    fun removeLastInput(): Expression

    /**
     * Returns true if the expression equals to the given input.
     */
    fun equalTo(input: String): Boolean

    /**
     * Makes a new copy of the expression with the given input that is appended to the end.
     */
    fun copyWith(input: String): Expression

    /**
     * Makes a new copy of the expression with the given input that is appended to the end.
     */
    fun copyWith(vararg input: String): Expression

    /**
     * Returns true if the expression starts with 0 and predicate function return true.
     */
    fun startsWithZero(predicate: () -> Boolean = { true }): Boolean
}