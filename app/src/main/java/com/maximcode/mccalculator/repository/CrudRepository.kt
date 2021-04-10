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

package com.maximcode.mccalculator.repository

import javax.inject.Inject
import com.maximcode.mccalculator.db.HistoryDao
import com.maximcode.mccalculator.dto.HistoryEntity
import com.maximcode.mccalculator.dto.History
import com.maximcode.mccalculator.models.SectionedHistory
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import java.util.*

/**
 * Uses for generic CRUD operations on a repository.
 */
class CrudRepository @Inject constructor(private val dao: HistoryDao) {

    fun add(history: History): Completable {
        return  dao.insert(HistoryEntity(
            expression = history.expression,
            expressionResult = history.expressionResult,
            date = Calendar.getInstance().time.time)
        )
    }

    fun history(): Flowable<SectionedHistory> = dao.loadHistory().map { list ->
        SectionedHistory(list)
    }

    fun clearAllRecords() = dao.clearAllRecords()
}
