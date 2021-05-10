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

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.maximcode.mccalculator.R
import com.maximcode.mccalculator.ui.history.historylist.HistoryAdapter
import com.maximcode.mccalculator.databinding.FragmentHistoryBinding
import com.maximcode.mccalculator.dto.HistoryState
import com.maximcode.mccalculator.ui.pager.PageFragment
import com.maximcode.mccalculator.ui.history.historylist.decorations.HistoryMarginDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment @Inject constructor(private val adapter: HistoryAdapter)
    : PageFragment<HistoryState, HistoryViewModel>(R.layout.fragment_history) {
    private val binding: FragmentHistoryBinding by viewBinding()
    override val viewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.showHistory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun render(state: HistoryState) {
        adapter.setData(state.history)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_history, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =  when(item.itemId) {
            android.R.id.home -> {
                findViewPager().setCurrentItem(0, true)
                true
            }

            R.id.actionClearHistory -> {
                viewModel.clearAllRecords()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }


    private fun setupRecyclerView() {
        binding.historyView.layoutManager = LinearLayoutManager(context)
        binding.historyView.adapter = adapter
        binding.historyView.addItemDecoration(
            HistoryMarginDecoration(requireContext(), 16, 16)
        )
    }
}