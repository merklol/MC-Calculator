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

import android.content.Context
import com.maximcode.mccalculator.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

enum class Strings {
    Radians, Degree, LastExpression, ClearAll, Clear, EmailAddress,
    EmailSubject, EmailBody, PrivacyPolicyUrl, Today, History
}

/**
 * Returns strings from the XML resources.
 */
@Singleton
class StringResources
@Inject constructor(@ApplicationContext context: Context) {
    private val resources = mapOf(
        Strings.Radians to context.getString(R.string.empty_string),
        Strings.Degree to context.getString(R.string.degree_label),
        Strings.LastExpression to context.getString(R.string.last_expression),
        Strings.ClearAll to context.getString(R.string.clearAll),
        Strings.Clear to context.getString(R.string.clear),
        Strings.EmailAddress to context.getString(R.string.email),
        Strings.EmailSubject to context.getString(R.string.feedback_email_subject),
        Strings.EmailBody to context.getString(R.string.feedback_email_body),
        Strings.PrivacyPolicyUrl to context.getString(R.string.privacy_policy_url),
        Strings.Today to context.getString(R.string.recycler_view_item_today),
        Strings.History to context.getString(R.string.action_history)
    )

    operator fun get(key: Strings) = resources[key] ?: ""
}