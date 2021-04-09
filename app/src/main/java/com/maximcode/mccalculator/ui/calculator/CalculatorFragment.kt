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

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.internal.findRootView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.maximcode.mccalculator.*
import com.maximcode.mccalculator.databinding.FragmentCalculatorBinding
import com.maximcode.mccalculator.dto.CalculatorState
import com.maximcode.mccalculator.models.expression.Expression
import com.maximcode.mccalculator.models.intents.SendEmailIntent
import com.maximcode.mccalculator.models.intents.ViewURLIntent
import com.maximcode.mccalculator.models.Keyboard
import com.maximcode.mccalculator.models.LoadedAd
import com.maximcode.mccalculator.models.StringResources
import com.maximcode.mccalculator.models.Strings
import com.maximcode.rxmvi.view.RxMviFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CalculatorFragment
@Inject constructor(
    private val keyboard: Keyboard,
    private val resources: StringResources,
    private val loadedAd: LoadedAd
    ): RxMviFragment<CalculatorState, CalculatorViewModel>(R.layout.fragment_calculator) {
    override val viewModel: CalculatorViewModel by viewModels()
    private val binding: FragmentCalculatorBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        loadedAd.load()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        keyboard.bindButtons(findRootView(requireActivity()), viewModel::bindButtons)
    }

    override fun render(state: CalculatorState) {
        renderExpression(state.expression)
        renderClearExpressionBtn(state.expression)
        renderLastExpression(state)
        renderAngleMeasureIndicator(state.isInRadians)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.actionHistory -> {
            loadedAd.show(requireActivity())
            findNavController().navigate(R.id.action_mainFragment_to_historyFragment)
            true
        }

        R.id.actionPrivacyPolicy -> {
            ViewURLIntent(resources[Strings.PrivacyPolicyUrl]).launch(requireActivity())
            true
        }

        R.id.actionSendFeedback -> {
            SendEmailIntent(resources[Strings.EmailAddress], resources[Strings.EmailSubject],
                resources[Strings.EmailBody]).launch(requireActivity())
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private fun renderExpression(expression: Expression) {
        binding.screen.expression.text = expression.toFormattedString()
    }

    private fun renderClearExpressionBtn(expression: Expression) {
        binding.keyboard.clearBtn.text = when {
            expression.equalTo("0") -> resources[Strings.ClearAll]
            else -> resources[Strings.Clear]
        }
    }

    private fun renderAngleMeasureIndicator(isInRadians: Boolean) {
        viewModel.updateAngleMeasure(isInRadians) {
            binding.screen.angleMeasure?.text = when {
                isInRadians -> resources[Strings.Radians]
                else -> resources[Strings.Degree]
            }
        }
    }

    private fun renderLastExpression(state: CalculatorState) {
        if(state.isNewExpression) {
            binding.screen.lastExpression.text = when {
                state.lastExpression.last.isNotEmpty() -> resources[Strings.LastExpression]
                    .format(state.lastExpression.toFormattedString())

                else -> state.lastExpression.toFormattedString()
            }
        }
    }
}