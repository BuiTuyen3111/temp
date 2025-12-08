package com.zip.lock.screen.wallpapers.data.source.remote.cloud

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseMgr @Inject constructor() {
    val remoteConfig by lazy {
        FirebaseRemoteConfig.getInstance()
    }
}