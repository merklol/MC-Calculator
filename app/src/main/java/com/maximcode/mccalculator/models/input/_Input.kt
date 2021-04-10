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
import com.maximcode.core.tables.BinaryOperations
import com.maximcode.core.tables.Shortcuts

/**
 * The table of superscripts.
 */
internal val superScript = hashMapOf(
    "0" to "\u2070", "1" to "\u00B9", "2" to "\u00B2", "3" to "\u00B3", "4" to "\u2074",
    "5" to "\u2075", "6" to "\u2076", "7" to "\u2077", "8" to "\u2078", "9" to "\u2079"
)

/**
 * Returns true if the input equals to the base lexeme [BaseLexemes.Dot].
 */
fun String.isDot() = this == BaseLexemes.Dot

/**
 * Returns true if the input is the zero digit.
 */
fun String.isZero() = this == "0"

/**
 * Returns true if the input is a digit.
 */
fun String.isDigit() = this in "0".."9"

/**
 * Returns true if the input is a numeric segment.
 */
fun String.isNumeric() = this.toDoubleOrNull() != null

/**
 * Returns true if the input does not equal zero.
 */
fun String.isNotZero() = this != "0"

/**
 * Returns true if the input is not a digit.
 */
fun String.isNotDigit() = this !in "0".."9"

/**
 * Converts the input to a superscript.
 */
fun String.toSuperScript() = superScript[this] ?: ""

/**
 * Returns true if the input is a positive or negative digit.
 */
fun String.isPositiveOrNegativeDigit() = this in "0".."9" || this in "-0".."-9"

/**
 * Returns true if the input equals to the shortcut [Shortcuts.TenWithExponent].
 */
@JvmName("_isTenWithExponent")
fun String?.isTenWithExponent() = this != null || this == Shortcuts.TenWithExponent

/**
 * Returns true if the input equals to the shortcut [Shortcuts.TenWithExponent].
 */
fun String.isTenWithExponent() = this == Shortcuts.TenWithExponent

/**
 * Returns true if the input does not equals to the shortcut [Shortcuts.TenWithExponent].
 */
fun String.isNotTenWithExponent() = this != Shortcuts.TenWithExponent

/**
 * Returns true if the input equals to the binary operation [BinaryOperations.Exponent].
 */
@JvmName("_isExponent")
fun String?.isExponent() = this != null || this == BinaryOperations.Exponent

/**
 * Returns true if the input equals to the binary operation [BinaryOperations.Exponent].
 */
fun String.isExponent() = this == BinaryOperations.Exponent

/**
 * Returns true if the input equals to the shortcut [Shortcuts.Squared].
 */
fun String.isSquared() = this == Shortcuts.Squared

/**
 * Returns true if the input equals to the shortcut [Shortcuts.Squared]
 * or the base lexeme [BaseLexemes.Dot].
 */
fun String.isSquaredOrDot() = this.isSquared() || this.isDot()

/**
 * Returns true if the input equals to the binary operation [BinaryOperations.Exponent]
 * or the shortcut [Shortcuts.Squared].
 */
fun String.isExponentOrSquared() = this.isSquared() || this.isExponent()

/**
 * Returns true if the input equals to the shortcut [Shortcuts.DoubleZero].
 */
fun String.isDoubleZero() = this == Shortcuts.DoubleZero

/**
 * Returns true if the input equals to the base lexeme [BaseLexemes.LeftBracket].
 */
fun String.hasLeftBracket() = this.contains(BaseLexemes.LeftBracket)