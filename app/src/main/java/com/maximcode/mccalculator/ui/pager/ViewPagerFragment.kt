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

package com.maximcode.mccalculator.ui.pager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.maximcode.mccalculator.R
import com.maximcode.mccalculator.databinding.FragmentViewpagerBinding
import com.maximcode.mccalculator.models.StringResources
import com.maximcode.mccalculator.models.Strings
import com.maximcode.mccalculator.ui.calculator.CalculatorFragment
import com.maximcode.mccalculator.ui.history.HistoryFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class ViewPagerFragment
@Inject constructor(
    private val calculatorFragment: CalculatorFragment,
    private val historyFragment: HistoryFragment,
    private val resources: StringResources) : Fragment(R.layout.fragment_viewpager) {

    private lateinit var pager: ViewPager2
    private val binding: FragmentViewpagerBinding by viewBinding()
    private val callback: OnPageChangeCallback = OnPageChangeCallback()

    val viewPager get() = pager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pager = binding.viewPager
        pager.adapter = ViewPagerAdapter(this, listOf(calculatorFragment, historyFragment))
        pager.registerOnPageChangeCallback(callback)
        pager.setPageTransformer { page, position ->
            page.translationY = position
            page.alpha = 1 - abs(position)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pager.unregisterOnPageChangeCallback(callback)
    }

    private fun enableShowHomeAsUp(showHomeAsUp: Boolean) {
        actionBar()?.setDisplayHomeAsUpEnabled(showHomeAsUp)
    }

    private fun setTitle(value: String) {
        actionBar()?.title = value
    }

    private fun actionBar() = (requireActivity() as AppCompatActivity).supportActionBar

    private inner class OnPageChangeCallback: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when(position) {
                ViewPagerAdapter.CalculatorPage -> {
                    setTitle("")
                    enableShowHomeAsUp(false)
                }

                ViewPagerAdapter.HistoryPage -> {
                    setTitle(resources[Strings.History])
                    enableShowHomeAsUp(true)
                }
            }
        }
    }
}