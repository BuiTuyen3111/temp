package com.zip.lock.screen.wallpapers.ads

import com.google.android.gms.ads.nativead.NativeAdOptions
import com.leansoft.ads.AdConfig
import com.zip.lock.screen.wallpapers.BuildConfig
import com.zip.lock.screen.wallpapers.R
import javax.inject.Inject

class AdConfigImpl @Inject constructor(private val adSharedPreference: AdSharedPreference) :
    AdConfig() {

    override fun enableAllAds(): Boolean {
        return super.enableAllAds() && !BuildConfig.DEBUG
    }

    override fun nativeAdChoicesPosition(): Int {
        return NativeAdOptions.ADCHOICES_TOP_LEFT
    }

    override fun getLayoutLoading(): Int {
        return R.layout.fragment_loading_dialog
    }
}