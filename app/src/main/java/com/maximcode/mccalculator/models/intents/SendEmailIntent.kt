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

package com.maximcode.mccalculator.models.intents

import android.app.Activity
import android.content.Intent

/**
 * Implements the [ActivityIntent] to send an email to the given email address.
 */
class SendEmailIntent(
    private val email: String,
    private val subject: String,
    private val body: String): ActivityIntent {

    private val intent = Intent(Intent.ACTION_SEND).also {
        it.type = "message/rfc822"
        it.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        it.putExtra(Intent.EXTRA_SUBJECT, subject)
        it.putExtra(Intent.EXTRA_TEXT, body)
    }

    override fun launch(activity: Activity) = activity.startActivity(intent)
}