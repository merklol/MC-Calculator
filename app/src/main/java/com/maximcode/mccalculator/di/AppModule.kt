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

package com.maximcode.mccalculator.di

import android.content.Context
import androidx.room.Room
import com.maximcode.core.evaluator.DefaultEvaluator
import com.maximcode.core.evaluator.Evaluator
import com.maximcode.mccalculator.R
import com.maximcode.mccalculator.db.AppDatabase
import com.maximcode.mccalculator.repository.CrudRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides app-level dependencies for this app.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesEvaluator(): Evaluator {
        return DefaultEvaluator()
    }

    @Provides
    @Singleton
    fun providesRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java,
            "history_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesCrudRepository(appDatabase: AppDatabase): CrudRepository {
        return CrudRepository(appDatabase.historyDao())
    }

    @Provides
    @Singleton
    fun providesKeyboardIds(): Array<Int> {
        return arrayOf(
            R.id.radBtn, R.id.degBtn, R.id.factorialBtn, R.id.leftBracketBtn, R.id.rightBracketBtn,
            R.id.percentBtn, R.id.clearBtn, R.id.sin1Btn, R.id.cos1Btn, R.id.tan1Btn, R.id.xyBtn,
            R.id.tenYBtn, R.id.squaredBtn, R.id.divideBtn, R.id.ln2Btn, R.id.sinBtn, R.id.lnBtn,
            R.id.digit7Btn, R.id.digit8Btn, R.id.digit9Btn, R.id.multiplyBtn, R.id.piBtn,
            R.id.cosBtn, R.id.logBtn, R.id.digit4Btn, R.id.digit5Btn, R.id.digit6Btn,
            R.id.subtractBtn, R.id.eBtn, R.id.tanBtn, R.id.sqrtBtn, R.id.digit1Btn,
            R.id.digit2Btn, R.id.digit3Btn, R.id.addBtn, R.id.ansBtn, R.id.expBtn,
            R.id.rndBtn, R.id.digit0Btn, R.id.digit00Btn, R.id.dotBtn, R.id.equalsBtn
        )
    }
}