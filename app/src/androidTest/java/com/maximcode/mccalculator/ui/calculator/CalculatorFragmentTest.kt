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

package com.maximcode.mccalculator.ui.calculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.maximcode.mccalculator.R
import com.maximcode.mccalculator.utils.InjectFragmentFactory
import com.maximcode.mccalculator.utils.ScreenshotTestRule
import com.maximcode.mccalculator.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class CalculatorFragmentTest {
    @get:Rule val hiltRule = HiltAndroidRule(this)
    @get:Rule val screenshotTestRule = ScreenshotTestRule()
    @Inject
    lateinit var factory: InjectFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragmentInHiltContainer<CalculatorFragment>(factory = factory) {}
    }

    @Test
    fun when_button_clicked_the_value_shows_on_the_output_view() {
        onView(withId(R.id.digit4Btn)).perform(click())
        onView(withId(R.id.expression)).check(matches(withText("4")))
    }

    @Test
    fun when_keyboard_button_clicked_and_clear_btn_clicked_once_output_view_shows_right_value() {
        onView(withId(R.id.addBtn)).perform(click(), click())
        onView(withId(R.id.clearBtn)).perform(click())
        onView(withId(R.id.expression)).check(matches(withText("0 + ")))
    }

    @Test
    fun when_expr_is_larger_than_1_clear_btn_is_CE() {
        onView(withId(R.id.addBtn)).perform(click(), click())
        onView(withId(R.id.clearBtn)).perform(click())
        onView(withId(R.id.clearBtn)).check(matches(withText("CE")))
    }

    @Test
    fun when_expr_size_is_1_and_equals_0_clear_btn_is_AC() {
        onView(withId(R.id.addBtn)).perform(click())
        onView(withId(R.id.clearBtn)).perform(click())
        onView(withId(R.id.clearBtn)).check(matches(withText("AC")))
    }

    @Test
    fun when_equals_btn_clicked_last_expression_is_correctly_formatted() {
        onView(withId(R.id.addBtn)).perform(click())
        onView(withId(R.id.digit7Btn)).perform(click())
        onView(withId(R.id.equalsBtn)).perform(click())
        onView(withId(R.id.lastExpression)).check(matches(withText("0 + 7 =")))
    }

    @Test
    fun when_equals_btn_clicked_expression_shows_its_result() {
        onView(withId(R.id.addBtn)).perform(click())
        onView(withId(R.id.digit7Btn)).perform(click())
        onView(withId(R.id.equalsBtn)).perform(click())
        onView(withId(R.id.expression)).check(matches(withText("7")))
    }
}