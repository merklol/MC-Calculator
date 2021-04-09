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

package com.maximcode.core.evaluator

import com.maximcode.core.lexer.DefaultLexer
import com.maximcode.core.parser.*
import com.maximcode.core.tables.precedenceTable
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.math.*

/**
 * Default implementation of the [Evaluator] interface.
 */
class DefaultEvaluator: Evaluator {
    private val parser = DefaultParser(precedenceTable, DefaultLexer())
    private val history = Stack<Answer>()

    override fun evaluateExpression(expression: String, isInRadian: Boolean): Number = try {
        val expressionResult = calculate(parser.buildAST("$expression;"), isInRadian)
        history.push(Answer(expression, expressionResult))
        expressionResult
    } catch (exception: Exception) {
        Double.MIN_VALUE
    }

    private fun calculate(nodes: List<Node>, isInRadian: Boolean): Number {
        val stack = Stack<Double>()
        nodes.forEach {
            when(it) {
                is Add -> stack.push(stack.pop() + stack.pop())
                is Multiply -> stack.push(stack.pop() * stack.pop())
                is Subtract -> {
                    val t = stack.pop()
                    stack.push(stack.pop() - t)
                }
                is Divide -> {
                    val t = stack.pop()
                    stack.push(stack.pop() / t)
                }
                is Percent -> stack.push(stack.pop() / 100)
                is ExponentialFunction -> {
                    val t = stack.pop()
                    stack.push(stack.pop() * (10.0.pow(t)))
                }
                is Factorial -> stack.push(factorial(stack.pop()))
                is SquareRoot -> stack.push(sqrt(stack.pop()))
                is Exponent -> {
                    val t = stack.pop()
                    stack.push(stack.pop().pow(t))
                }
                is Sine -> stack.push(
                    calculateTrigFunctions(isInRadian, stack.pop()) { value ->
                        sin(value)
                    }
                )
                is ArcSine -> stack.push(
                    calculateITrigFunctions(isInRadian, stack.pop()) { value ->
                        asin(value)
                    }
                )
                is Cosine -> stack.push(
                    calculateTrigFunctions(isInRadian, stack.pop()){ value ->
                        cos(value)
                    }
                )
                is ArcCosine -> stack.push(
                    calculateITrigFunctions(isInRadian, stack.pop()) { value ->
                        acos(value)
                    }
                )
                is Tangent -> stack.push(
                    calculateTrigFunctions(isInRadian, stack.pop()) { value ->
                        tan(value)
                    }
                )
                is ArcTangent -> stack.push(
                    calculateITrigFunctions(isInRadian, stack.pop()) { value ->
                        atan(value)
                    }
                )
                is Logarithm -> stack.push(log10(stack.pop()))
                is NLogarithm -> stack.push(ln(stack.pop()))
                is LastAnswer -> stack.push(history.peek().expressionResult.toDouble())
                is Pi -> stack.push(Math.PI)
                is NaturalLog2 -> stack.push(ln(2.0))
                is EulerNumber -> stack.push(Math.E)
                else -> stack.push(it.value.toDouble())
            }
        }
        return truncate(stack.pop())
    }

    private fun calculateTrigFunctions(
        isInRadian: Boolean, value: Double, action: (Double) -> Double): Double =
        when(isInRadian) {
            true ->action(value)
            else -> action(Math.toRadians(value))
        }

    private fun calculateITrigFunctions(
        isInRadian: Boolean, value: Double, action: (Double) -> Double): Double =
        when(isInRadian) {
            true -> action(value)
            else -> Math.toDegrees(action(value))
        }

    private fun truncate(value: Double): Number = when(floor(value) == value) {
        true -> value.toInt()
        else -> roundNumber(value)
    }

    private fun roundNumber(value: Double, places: Int = 10) = when(value.isNaN()) {
        true -> value
        else -> BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP).toDouble()
    }

    private fun factorial(number: Double): Double = try {
        when {
            number % 1 != 0.0 -> -1.0
            else -> when (number) {
                0.0 -> 1.0
                else -> number * factorial(number - 1)
            }
        }
    } catch (error: StackOverflowError) {
        Double.MIN_VALUE
    }
}