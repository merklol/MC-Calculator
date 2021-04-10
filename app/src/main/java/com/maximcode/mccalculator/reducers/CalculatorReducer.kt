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
import com.maximcode.mccalculator.models.rules.*
import com.maximcode.rxmvi.core.Reducer
import com.maximcode.rxmvi.core.actions.Action
import javax.inject.Inject

/**
 * Uses for the calculator screen's store.
 */
class CalculatorReducer @Inject constructor(private val ruleEngine: RuleEngine)
    : Reducer<CalculatorState> {

    override fun reduce(state: CalculatorState, action: Action) = when (action) {
        is CalculatorActions.KeyboardButtonClicked ->
            ruleEngine.processOrElse(state, action.payload) {
            state.copy(expression = state.expression.copyWith(action.payload))
        }

        is CalculatorActions.ClearButtonClicked -> when {
            state.expression.length > 1 ->
                state.copy(expression = state.expression.removeLastInput())
            state.expression.isNotZero() -> state.copy(expression = expressionOf("0"))
            else -> state.copy(lastExpression = expressionOf(""))
        }

        is CalculatorActions.RadiansButtonClicked -> state.copy(isInRadians = true)
        is CalculatorActions.DegreeButtonClicked -> state.copy(isInRadians = false)
        is CalculatorEffects.OnCalculationCompleted -> action.payload
        is CalculatorEffects.OnRandomNumberGenerated -> action.payload

        else -> state
    }
}