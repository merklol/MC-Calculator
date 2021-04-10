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

import com.maximcode.core.tables.BinaryOperations
import com.maximcode.core.tables.NaN
import com.maximcode.core.tables.Error
import com.maximcode.mccalculator.models.input.*

/**
 * Default implementation of the [Expression] interface.
 */
class DefaultExpression(private val segments: List<String>): Expression {

    override val length get() = segments.size
    override val last get() = segments.last()
    override val secondLast get() = if(length >= 2) segments[segments.lastIndex - 1] else null

    override fun component1() = last
    override fun component2() = secondLast
    override fun isNaN() = segments.first() == NaN
    override fun isError() = length == 1 && segments.first() == Error
    override fun isNotZero() = length == 1 && segments.first().isNotZero()
    override fun toString() = segments.joinToString(separator = "")
    override fun equalTo(input: String) = length == 1 && segments.first() == input

    override fun removeLastInput() =
        DefaultExpression(segments.dropLast(1))

    override fun copyWith(input: String) =
        DefaultExpression(listOf(*segments.toTypedArray(), input))

    override fun copyWith(vararg input: String) =
        DefaultExpression(listOf(*segments.toTypedArray(),*input))

    override fun startsWithZero(predicate: () -> Boolean) =
        length == 1 && segments.first().isZero() && predicate()

    override fun toFormattedString() = segments.fold(listOf(""), { accumulator, input -> when {
        accumulator.last().contains(BinaryOperations.Exponent) ->
            listOf(*accumulator.dropLast(1).toTypedArray(),
                accumulator.last().dropLast(1) + input.toSuperScript())

        input.isSquared()-> accumulator + input.drop(1).toSuperScript()

        input == BinaryOperations.Multiply || input == BinaryOperations.Divide
                || input == BinaryOperations.Add -> accumulator + " $input "

        input == BinaryOperations.Subtract -> FormattedInput(input, accumulator).asList()

        else -> accumulator + input
    }}).joinToString(separator = "")
}