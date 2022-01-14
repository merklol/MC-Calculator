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
import android.content.Context
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.maximcode.mccalculator.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Loads and shows InterstitialAd.
 */
@Singleton
class InterstitialAdLoader
@Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var interstitialAd: InterstitialAd? = null

    private val adLoadCallback = object : InterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(p0: LoadAdError) {
            super.onAdFailedToLoad(p0)
            interstitialAd = null
        }

        override fun onAdLoaded(p0: InterstitialAd) {
            super.onAdLoaded(p0)
            interstitialAd = p0
        }
    }

    fun load() {
        InterstitialAd.load(
            context, BuildConfig.InterstitialId,
            AdRequest.Builder().build(), adLoadCallback
        )
    }

    fun show(activity: Activity) {
        interstitialAd?.show(activity)
    }
}

class LoadableInterstitialAd : InterstitialAd() {
    private val interstitialAd: InterstitialAd? = null

    override fun getAdUnitId() = interstitialAd?.adUnitId ?: ""

    override fun show(activity: Activity) {
        interstitialAd?.show(activity)
    }

    override fun setFullScreenContentCallback(
        fullScreenContentCallback: FullScreenContentCallback?
    ) {
        interstitialAd?.fullScreenContentCallback = fullScreenContentCallback
    }

    override fun getFullScreenContentCallback(): FullScreenContentCallback? =
        interstitialAd?.fullScreenContentCallback

    override fun setImmersiveMode(enabled: Boolean) {
       interstitialAd?.setImmersiveMode(enabled)
    }

    override fun getResponseInfo() = interstitialAd?.responseInfo

    override fun setOnPaidEventListener(onPaidEventListener: OnPaidEventListener?) {
        interstitialAd?.onPaidEventListener = onPaidEventListener
    }

    override fun getOnPaidEventListener(): OnPaidEventListener? =
        interstitialAd?.onPaidEventListener
}

fun loadInterstitialAd(activity: Activity, adUnitId: String): Any {
    return object {
        private var ad: InterstitialAd? = null

        operator fun invoke() {
            InterstitialAd.load(activity.applicationContext, adUnitId, AdRequest.Builder().build(),
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        super.onAdLoaded(interstitialAd)
                        ad = interstitialAd
                    }
                })
        }
    }
}