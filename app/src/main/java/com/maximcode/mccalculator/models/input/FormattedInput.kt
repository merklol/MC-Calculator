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

package com.maximcode.mccalculator.models.input

import com.maximcode.core.tables.BaseLexemes
import com.maximcode.core.tables.Functions
import com.maximcode.core.tables.Shortcuts

/**
 * A wrapper that implements the [Input] interface and surrounds the original input
 * with white spaces if it meets certain criteria.
 */
class FormattedInput(private val string: String, private val previousString: List<String>): Input {
    private val lexemes = hashSetOf(
        Shortcuts.LastAnswer, BaseLexemes.RightBracket, Functions.Factorial, Functions.Percent
    )

    override fun asList(): List<String> {
        return when {
            previousString.last().isNumeric() ||
                    lexemes.contains(previousString.last()) -> previousString + " $string "

            else -> when (superScript.containsValue(previousString.last())) {
                true -> previousString + " $string "
                else -> previousString + string
            }
        }
    }
}