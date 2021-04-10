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

package com.maximcode.mccalculator.middlewares

import com.maximcode.core.evaluator.Evaluator
import com.maximcode.mccalculator.actions.CalculatorActions
import com.maximcode.mccalculator.actions.CalculatorEffects
import com.maximcode.mccalculator.dto.History
import com.maximcode.mccalculator.dto.CalculatorState
import com.maximcode.mccalculator.models.expression.Expression
import com.maximcode.mccalculator.repository.CrudRepository
import com.maximcode.mccalculator.models.expression.expressionOf
import com.maximcode.rxmvi.core.Middleware
import com.maximcode.rxmvi.core.actions.Action
import com.maximcode.rxmvi.utils.plusAssign
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * Calculates the expression and save it to the local database.
 */
class CalculatingExpression
@Inject constructor(private val repository: CrudRepository, private val evaluator: Evaluator)
    : Middleware<CalculatorState> {
    private val disposables = CompositeDisposable()

    override fun bind(state: Observable<CalculatorState>, actions: Observable<Action>)
    : Observable<Action> = actions.ofType(CalculatorActions.EqualBtnClicked::class.java)
        .withLatestFrom(state) { action, currentState -> action to currentState }
        .flatMap { (action, state) ->
            Observable.just(action).observeOn(AndroidSchedulers.mainThread()).map<CalculatorEffects> {
                val result = evaluator.evaluateExpression(
                    state.expression.toString(), state.isInRadians
                )
                CalculatorEffects.OnCalculationCompleted(state.copy(
                    expression = expressionOf(result), lastExpression = state.expression,
                    history = saveHistory(state.expression, result), isNewExpression = true)
                )
            }
        }

    private fun saveHistory(expression: Expression, result: Number): History {
        val history = History(
            expression = expression.toFormattedString(), expressionResult = result.toString()
        )
        disposables += repository.add(history)
            .subscribeOn(Schedulers.io())
            .subscribe { disposables.clear() }

        return history
    }
}