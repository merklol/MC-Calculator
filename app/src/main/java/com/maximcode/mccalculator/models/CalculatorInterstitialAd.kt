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

import android.app.Activity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.maximcode.mccalculator.BuildConfig

/**
 * Loads and shows InterstitialAd.
 */
class CalculatorInterstitialAd constructor(private val activity: Activity) {
    private var ad: InterstitialAd? = null

    private val adLoadCallback = object : InterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(error: LoadAdError) {
            super.onAdFailedToLoad(error)
            ad = null
        }

        override fun onAdLoaded(ad: InterstitialAd) {
            super.onAdLoaded(ad)
            this@CalculatorInterstitialAd.ad = ad
        }
    }

    fun load() {
        InterstitialAd.load(
            activity.applicationContext, BuildConfig.InterstitialAdId,
            AdRequest.Builder().build(), adLoadCallback
        )
    }

    fun show(activity: Activity) {
        ad?.show(activity)
    }
}
