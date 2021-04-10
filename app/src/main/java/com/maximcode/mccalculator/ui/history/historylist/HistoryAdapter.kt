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

package com.maximcode.mccalculator.ui.history.historylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maximcode.mccalculator.databinding.HistoryItemViewBinding
import com.maximcode.mccalculator.databinding.HistoryItemWithHeaderViewBinding
import com.maximcode.mccalculator.dto.HistoryItem
import com.maximcode.mccalculator.dto.HistoryItemWithHeader
import com.maximcode.mccalculator.dto.RecyclerViewItem
import com.maximcode.mccalculator.models.StringResources
import com.maximcode.mccalculator.models.Strings
import com.maximcode.mccalculator.utils.HistoryDiffUtil
import com.maximcode.mccalculator.utils.isSameDate
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HistoryAdapter @Inject constructor(private val resources: StringResources)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<RecyclerViewItem>()
    private val dateFormat: DateFormat =
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    fun setData(data: List<RecyclerViewItem>) {
        val result = DiffUtil.calculateDiff(HistoryDiffUtil(items, data))
        items.clear()
        items.addAll(data)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType) {
        0 -> HistoryViewHolderWithHeader(resources,
            HistoryItemWithHeaderViewBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        )
        else -> HistoryViewHolder(resources,
            HistoryItemViewBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        )
    }

    override fun getItemViewType(position: Int) = when(items[position]) {
        is HistoryItemWithHeader -> 0
        is HistoryItem -> 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HistoryViewHolderWithHeader ->
                holder.bind(dateFormat, items[position] as HistoryItemWithHeader)

            is HistoryViewHolder ->
                holder.bind(items[position] as HistoryItem)
        }
    }

    override fun getItemCount() = items.size

    class HistoryViewHolderWithHeader(
        private val stringResources: StringResources,
        private val binding: HistoryItemWithHeaderViewBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(dateFormat: DateFormat, item: HistoryItemWithHeader) = with(itemView) {
            binding.historyItemExpression.text = stringResources[Strings.LastExpression]
                .format(item.expression)

            binding.historyItemExpressionResult.text = item.expressionResult
            binding.historyItemDate.text = when {
                Calendar.getInstance().time.time.isSameDate(item.date) ->
                    stringResources[Strings.Today]

                else -> dateFormat.format(Date(item.date))
            }
        }
    }

    class HistoryViewHolder(
        private val stringResources: StringResources, private val binding: HistoryItemViewBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HistoryItem) = with(itemView) {
            binding.historyItemExpression.text = stringResources[Strings.LastExpression]
                .format(item.expression)
            binding.historyItemExpressionResult.text = item.expressionResult
        }
    }
}