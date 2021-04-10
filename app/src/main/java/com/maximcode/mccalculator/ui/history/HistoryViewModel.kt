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

import com.maximcode.mccalculator.actions.HistoryActions
import com.maximcode.mccalculator.dto.HistoryState
import com.maximcode.mccalculator.repository.CrudRepository
import com.maximcode.rxmvi.core.store.Store
import com.maximcode.rxmvi.utils.plusAssign
import com.maximcode.rxmvi.view.RxMviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val store: Store<HistoryState>, private val repository: CrudRepository)
    : RxMviViewModel<HistoryState>(store) {

    override val disposables = CompositeDisposable()

    fun showHistory() {
        disposables += repository.history()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                disposables += store.dispatch { HistoryActions.ShowHistory(it.toList()) }
            }
    }

    fun clearAllRecords() {
        disposables += repository.clearAllRecords()
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}