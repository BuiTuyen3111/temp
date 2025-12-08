package com.zip.lock.screen.wallpapers.ext

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.util.TypedValue
import androidx.core.content.ContextCompat
import java.io.File

fun Context.dpToPx(dp: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    ).toInt()
}

fun Context.readJsonFromAssets(fileName: String): String {
    return assets.open(fileName).bufferedReader().use { it.readText() }
}
