package com.zip.lock.screen.wallpapers.ext

import com.zip.lock.screen.wallpapers.BuildConfig
import android.content.Context
import android.content.res.Resources
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.runTryCatch(block: () -> Unit) {
    try {
        block()
    } catch (e: Throwable) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        }
    }
}

fun runTryCatch(block: () -> Unit) {
    try {
        block()
    } catch (e: Throwable) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        }
    }
}

fun Float.toPx(): Float {
    return this * Resources.getSystem().displayMetrics.density
}

fun Int.dpToPx(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}