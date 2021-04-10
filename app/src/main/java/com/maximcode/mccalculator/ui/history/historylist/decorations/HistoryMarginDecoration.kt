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

package com.maximcode.mccalculator.ui.history.historylist.decorations

import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HistoryMarginDecoration(context: Context, private var marginHorizontal: Int,
    private var marginVertical: Int): RecyclerView.ItemDecoration() {

    init {
        val metrics: DisplayMetrics = context.resources.displayMetrics
        marginHorizontal = marginHorizontal.applyDimension(metrics)
        marginVertical = marginVertical.applyDimension(metrics)
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if(position.hasItemPositionInAdapter()) {
            applyMargins(position, outRect)
        }
    }

    private fun Int.hasItemPositionInAdapter() = this != RecyclerView.NO_POSITION

    private fun Int.firstItemPositionInAdapter() = this == 0

    private fun Int.applyDimension(metrics: DisplayMetrics) =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()

    private fun applyMargins(position: Int, viewItem: Rect) {
        if(position.firstItemPositionInAdapter()) viewItem.top = marginVertical
        viewItem.left = marginHorizontal
        viewItem.right = marginHorizontal
        viewItem.bottom = marginVertical
    }
}