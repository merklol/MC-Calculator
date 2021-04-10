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

package com.maximcode.mccalculator.di

import com.maximcode.core.evaluator.Evaluator
import com.maximcode.mccalculator.repository.CrudRepository
import com.maximcode.mccalculator.dto.CalculatorState
import com.maximcode.mccalculator.middlewares.CalculatingExpression
import com.maximcode.mccalculator.middlewares.GeneratingRandomNumber
import com.maximcode.mccalculator.models.rules.*
import com.maximcode.mccalculator.models.rules.subrules.*
import com.maximcode.mccalculator.reducers.CalculatorReducer
import com.maximcode.rxmvi.core.Middleware.Companion.middlewares
import com.maximcode.rxmvi.core.store.Store
import com.maximcode.rxmvi.core.store.createStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Provides dependencies for the Calculator ViewModel.
 */
@Module
@InstallIn(ViewModelComponent::class)
object CalculatorModule {

    @Provides
    @ViewModelScoped
    fun providesStore(ruleEngine: RuleEngine, repository: CrudRepository, evaluator: Evaluator)
    : Store<CalculatorState> {
        val middlewares =  middlewares(CalculatingExpression(repository, evaluator),
            GeneratingRandomNumber(CurrentInputIsRandomNumber()))

        return createStore(CalculatorReducer(ruleEngine), CalculatorState(), middlewares)
    }
}