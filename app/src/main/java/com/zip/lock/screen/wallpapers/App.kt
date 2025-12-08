package com.zip.lock.screen.wallpapers

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.leansoft.ads.AdManager
import com.leansoft.ads.AdsApplication
import com.zip.lock.screen.wallpapers.ads.AdPlacement
import com.zip.lock.screen.wallpapers.data.source.local.pref.PreferenceHelper
import com.zip.lock.screen.wallpapers.data.source.local.session.AppSession
import com.zip.lock.screen.wallpapers.data.source.remote.cloud.FirebaseMgr
import com.zip.lock.screen.wallpapers.utils.NetworkUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: AdsApplication() {

    @Inject
    lateinit var session: AppSession

    @Inject
    lateinit var pref: PreferenceHelper

    @Inject
    lateinit var networkUtils: NetworkUtils

    @Inject
    lateinit var firebaseMgr: FirebaseMgr

    override fun onCreate() {
        super.onCreate()
        instance = this
        ProcessLifecycleOwner.get().lifecycle.addObserver(MyObserver())
        AdManager.instance.canShowAdResume = false
    }

    companion object {
        lateinit var instance: App
    }
}

class MyObserver : DefaultLifecycleObserver {
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d("App", "App in foreground")

        if (!AdManager.instance.checkCanShowAdResume()) return
        if (!AdManager.instance.adEnablePlacement(AdPlacement.INTER_RESUME.key)) return
        AdManager.instance.showInterAd(AdPlacement.INTER_RESUME.key, true, true, null) {}
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d("App", "App in background")
    }
}