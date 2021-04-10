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
import com.maximcode.mccalculator.models.rules.subrules.*
import com.maximcode.rxmvi.core.actions.Action
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class GeneratingRandomNumberTest {
    private val subRule: SubRule = mockk()
    private val testObserver = TestObserver<Action>()


    @Before
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        every { subRule.apply(any(), any()) } returns CalculatorState()

        GeneratingRandomNumber(subRule).bind(
            Observable.just(CalculatorState()),
            Observable.just(CalculatorActions.RandomButtonClicked)).subscribe(testObserver)
    }

    @Test
    fun `when random button clicked returns OnRandomNumberGenerated`() {
        val effect = testObserver.values()[0]
        assertThat(effect, instanceOf(CalculatorEffects.OnRandomNumberGenerated::class.java))
    }
}