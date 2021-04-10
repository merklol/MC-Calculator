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

import com.maximcode.mccalculator.actions.CalculatorActions
import com.maximcode.mccalculator.actions.CalculatorEffects
import com.maximcode.mccalculator.dto.CalculatorState
import com.maximcode.mccalculator.models.rules.subrules.SubRule
import com.maximcode.rxmvi.core.Middleware
import com.maximcode.rxmvi.core.actions.Action
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * Generates a random number.
 */
class GeneratingRandomNumber @Inject constructor(private val subRule: SubRule)
    : Middleware<CalculatorState> {

    override fun bind(state: Observable<CalculatorState>, actions: Observable<Action>)
    : Observable<Action> = actions.ofType(CalculatorActions.RandomButtonClicked::class.java)
        .withLatestFrom(state) { action, currentState -> action to currentState }
        .flatMap { (action, state) -> Observable.just(action)
            .observeOn(AndroidSchedulers.mainThread()).map<CalculatorEffects> {
                CalculatorEffects.OnRandomNumberGenerated(subRule.apply(state, Math.random().toString()))
            }
        }
}