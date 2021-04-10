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

package com.maximcode.mccalculator

import com.maximcode.mccalculator.middlewares.CalculatingExpressionTest
import com.maximcode.mccalculator.middlewares.GeneratingRandomNumberTest
import com.maximcode.mccalculator.models.SectionedHistoryTest
import com.maximcode.mccalculator.models.expression.DefaultExpressionTest
import com.maximcode.mccalculator.models.input.FormattedInputTest
import com.maximcode.mccalculator.models.rules.*
import com.maximcode.mccalculator.models.rules.subrules.*
import com.maximcode.mccalculator.reducers.HistoryReducerTest
import com.maximcode.mccalculator.reducers.CalculatorReducerTest
import com.maximcode.mccalculator.utils.DateKtTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CurrentInputIsDigitTest::class,
    CurrentInputIsDotTest::class,
    CurrentInputIsNotDigitOrShortcutTest::class,
    CurrentInputIsRandomNumberTest::class,
    CurrentInputIsSquaredTest::class,
    DefaultTest::class,
    PreviousInputIsSquaredTest::class,
    PreviousIsLeftBracketTest::class,
    FormattedInputTest::class,
    DefaultExpressionTest::class,
    CalculatingExpressionTest::class,
    GeneratingRandomNumberTest::class,
    CurrentInputIsPercentTest::class,
    ExpressionHasDoubleDotTest::class,
    NewExpressionTest::class,
    PreviousInputIsBasicOperatorsTest::class,
    PreviousInputIsExponentTest::class,
    PreviousInputIsFactorialTest::class,
    SectionedHistoryTest::class,
    DateKtTest::class,
    CalculatorReducerTest::class,
    HistoryReducerTest::class
)
class AllTests