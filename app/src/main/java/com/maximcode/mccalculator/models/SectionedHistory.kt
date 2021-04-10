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

package com.maximcode.mccalculator.models

import com.maximcode.mccalculator.dto.HistoryEntity
import com.maximcode.mccalculator.dto.HistoryItem
import com.maximcode.mccalculator.dto.HistoryItemWithHeader
import com.maximcode.mccalculator.dto.RecyclerViewItem
import com.maximcode.mccalculator.utils.isSameDate

/**
 * A sorted history by date from a list of the [HistoryEntity] for the HistoryAdapter.
 */
class SectionedHistory(private val entities: List<HistoryEntity>) {

    fun toList(): List<RecyclerViewItem> = when(entities.isEmpty()) {
        true -> listOf()
        else -> createSections(entities.sortedBy { it.date }.reversed())
    }

    private fun createSections(sorted: List<HistoryEntity>): MutableList<RecyclerViewItem> {
        val first = sorted.first()
        val sortedViews = mutableListOf<RecyclerViewItem>(HistoryItemWithHeader(
            first.id, first.expression, first.expressionResult, first.date))

        sorted.drop(1).fold(first) { acc, viewItem -> when(viewItem.date.isSameDate(acc.date)) {
            true -> sortedViews.add(HistoryItem(
                viewItem.id, viewItem.expression, viewItem.expressionResult)
            )
            else -> sortedViews.add(HistoryItemWithHeader(
                viewItem.id, viewItem.expression, viewItem.expressionResult, viewItem.date)
            )
            }
            viewItem
        }
        return sortedViews
    }
}