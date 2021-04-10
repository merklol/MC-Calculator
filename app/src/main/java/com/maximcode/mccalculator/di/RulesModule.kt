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

import com.maximcode.mccalculator.models.rules.*
import com.maximcode.mccalculator.models.rules.subrules.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides dependencies for the Rule Engine.
 */
@Module
@InstallIn(SingletonComponent::class)
object RulesModule {

    @Provides
    @Singleton
    fun providesRuleEngine(rules: HashSet<Rule>): RuleEngine {
        return RuleEngine(rules)
    }

    @Provides
    @Singleton
    fun providesSubRules(): ArrayList<SubRule> {
        return arrayListOf(CurrentInputIsDot(), CurrentInputIsSquared(), CurrentInputIsDigit(),
            PreviousInputIsSquared(), PreviousIsLeftBracket(), CurrentInputIsNotDigitOrShortcut(),
            Default()
        )
    }

    @Provides
    @Singleton
    fun providesRules(subRules: ArrayList<SubRule>): HashSet<Rule> {
        return hashSetOf(NewExpression(), ExpressionHasDoubleDot(),
            PreviousInputIsExponent(subRules), CurrentInputIsPercent(), PreviousInputIsFactorial(),
            PreviousInputIsBasicOperators()
        )
    }
}