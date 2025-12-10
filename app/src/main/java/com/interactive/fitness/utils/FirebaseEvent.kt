package com.interactive.fitness.utils

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.interactive.fitness.ext.runTryCatch

object FirebaseEvent {
    fun logFirebaseEvent(context: Context, eventName: String, bundle: Bundle? = null) {
        runTryCatch {
            val analytics = FirebaseAnalytics.getInstance(context)
            analytics.logEvent(eventName, bundle)
        }
    }
}