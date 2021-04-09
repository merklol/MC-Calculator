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

import com.maximcode.core.Token
import com.maximcode.core.tables.*

/**
 * Default implementation of the [Lexer] interface.
 */
class DefaultLexer(private val buffer: StringBuilder = StringBuilder()): Lexer {

    override fun tokenize(expression: String): List<Token> {
        val lexemes: MutableList<Token> = expression.fold(mutableListOf(), { accumulator, value ->
            when {
                baseLanguage.contains(value) -> {
                    if(buffer.isNotEmpty()) {
                        accumulator += buffer.toString()
                        buffer.clear()
                    }
                    if(value != ';') accumulator += value.toString()
                }
                else -> buffer.append(value)
            }
            accumulator
        })
        moveUnaryMinuses(lexemes)
        return lexemes.filter { it != "" }
    }

    private fun moveUnaryMinuses(lexemes: MutableList<Token>) {
        lexemes.indices.forEach {
            if(lexemes[it].isSubtract()) {
                if(it <= 0 || !lexemes[it - 1].isNumeric() && !lexemes[it - 1].isLastAnswer()) {
                    lexemes[it] = ""
                    lexemes[it + 1] = "-${lexemes[it + 1]}"
                }
            }
        }
    }
}

internal fun String.isNumeric() = this.toDoubleOrNull() != null

internal fun String.isLastAnswer() = this == Shortcuts.LastAnswer

internal fun String.isSubtract() = this == BinaryOperations.Subtract