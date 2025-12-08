package com.zip.lock.screen.wallpapers.data.source.local.pref

import android.content.Context
import com.zip.lock.screen.wallpapers.utils.AppConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceHelper @Inject constructor(
    @ApplicationContext context: Context
) : Preferences(context, AppConstants.PREF_NAME) {
    var rate by booleanPref("rateApp", false)
}