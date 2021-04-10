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

package com.maximcode.mccalculator.reducers

import com.maximcode.mccalculator.actions.CalculatorActions
import com.maximcode.mccalculator.actions.CalculatorEffects
import com.maximcode.mccalculator.dto.CalculatorState
import com.maximcode.mccalculator.models.expression.expressionOf
import com.maximcode.mccalculator.models.rules.RuleEngine
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class CalculatorReducerTest {
    private val ruleEngine = RuleEngine(hashSetOf())
    private val reducer = CalculatorReducer(ruleEngine)

    @Test
    fun `when action(Btn Clicked) returns a copy of state with appended payload to expression`() {
        val result = reducer.reduce(CalculatorState(), CalculatorActions.KeyboardButtonClicked("5"))
        assertThat(result.expression.toString(), `is`("05"))
    }

    @Test
    fun `when action(Clear) returns a copy of state with removed last value from expression`() {
        val state = CalculatorState(expression = expressionOf("5", "5"))
        val result = reducer.reduce(state, CalculatorActions.ClearButtonClicked)
        assertThat(result.expression.toString(), `is`("5"))
    }

    @Test
    fun `when action(Clear), expr size is 1 returns a copy of state with new expression`() {
        val state = CalculatorState(expression = expressionOf("5"))
        val result = reducer.reduce(state, CalculatorActions.ClearButtonClicked)
        assertThat(result.expression.toString(), `is`("0"))
    }

    @Test
    fun `when action(Clear), expr equals 0 returns a copy of state with new last expression`() {
        val state = CalculatorState(expression = expressionOf("0"))
        val result = reducer.reduce(state, CalculatorActions.ClearButtonClicked)
        assertThat(result.lastExpression.toString(), `is`(""))
    }

    @Test
    fun `when action(Rad Btn Clicked) returns new state with isInRadians true`() {
        val result = reducer.reduce(CalculatorState(), CalculatorActions.RadiansButtonClicked)
        assertThat(result.isInRadians, `is`(true))
    }

    @Test
    fun `when action(Deg Btn Clicked) returns a new state with isInRadians false`() {
        val result = reducer.reduce(CalculatorState(), CalculatorActions.DegreeButtonClicked)
        assertThat(result.isInRadians, `is`(false))
    }

    @Test
    fun `when action(On Calc Completed) returns a new state with expr 0 and isExpression true`() {
        val state = CalculatorState()
        val result = reducer.reduce(state, CalculatorEffects.OnCalculationCompleted(state))
        assertThat(result.expression.toString(), `is`("0"))
        assertThat(result.isNewExpression, `is`(true))
    }

    @Test
    fun `when action(On Rnd Completed) returns payload`() {
        val state = CalculatorState()
        val result = reducer.reduce(state, CalculatorEffects.OnRandomNumberGenerated(state))
        assertThat(result.expression.toString(), `is`("0"))
    }
}