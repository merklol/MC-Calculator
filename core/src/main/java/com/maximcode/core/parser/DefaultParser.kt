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

import com.maximcode.core.lexer.Lexer
import com.maximcode.core.toNode
import com.maximcode.core.tables.BaseLexemes
import com.maximcode.core.tables.Constants
import com.maximcode.core.tables.precedenceTable
import java.util.*

/**
 * Default implementation of the [Parser] interface.
 */
class DefaultParser(
    private val table: Map<String, Int> = precedenceTable,
    private val lexer: Lexer): Parser {

    override fun buildAST(expression: String): List<Node> {
        val stack = Stack<Node>()
        val tokens = lexer.tokenize(expression).fold(mutableListOf<Node>(), { accumulator, value ->
            when {
                table.containsKey(value) -> {
                    accumulator += collect(stack, listOf()) {
                        stack.isNotEmpty() && isHigherPrecedence(
                            value, table, stack.peek()
                        )
                    }
                    stack.push(value.toNode())
                }
                value == Constants.PI -> accumulator += Pi(value)
                value == Constants.EulerNumber -> accumulator += EulerNumber(value)
                value == Constants.LN2 -> accumulator += NaturalLog2(value)

                value == BaseLexemes.RightBracket -> {
                    accumulator += collect(stack, listOf()) {
                        stack.peek().value != BaseLexemes.LeftBracket
                    }
                    stack.pop()
                }

                value == BaseLexemes.LeftBracket -> stack.push(Any(value))
                else -> accumulator += Any(value)
            }
            accumulator
        })
        tokens += collect(stack, listOf()) { stack.isNotEmpty() }
        return tokens
    }

    private tailrec fun collect(
        stack: Stack<Node>, accumulator: List<Node>,
        predicate: () -> Boolean): List<Node> = when (predicate()) {
        true -> collect(stack, accumulator + stack.pop(), predicate)
        else -> accumulator
    }

    private fun isHigherPrecedence(value: String, operations: Map<String, Int>, node: Node) =
        operations.containsKey(node.value) && operations[node.value]!! >= operations[value]!!
}