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

package com.maximcode.mccalculator.ui.history

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.maximcode.mccalculator.R
import com.maximcode.mccalculator.utils.InjectFragmentFactory
import com.maximcode.mccalculator.utils.ScreenshotTestRule
import com.maximcode.mccalculator.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class HistoryFragmentTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)
    @get:Rule val screenshotTestRule = ScreenshotTestRule()
    private lateinit var navController: NavController
    @Inject
    lateinit var factory: InjectFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        UiThreadStatement.runOnUiThread { navController.setGraph(R.navigation.nav_graph) }

        launchFragmentInHiltContainer<HistoryFragment>(
            factory = factory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun when_fragment_is_created_shows_the_history_list() {
        Thread.sleep(2000)
    }

    @Test
    fun when_arrow_up_clicked_it_navigates_back_to_the_main_fragment() {
        Espresso.pressBackUnconditionally()
        assertThat(navController.currentDestination?.id, `is`(R.id.mainFragment))
    }
}