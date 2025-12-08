package com.zip.lock.screen.wallpapers.utils

import com.zip.lock.screen.wallpapers.BuildConfig
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.graphics.createBitmap
import androidx.core.net.toUri
import androidx.core.os.postDelayed
import com.google.android.play.core.review.ReviewManagerFactory

object AppUtils {

    fun showShortToast(context: Context, msg: String, forceShow: Boolean = false) {
        if (BuildConfig.DEBUG || forceShow) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    fun showLongToast(context: Context, msg: String) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
    }

    fun postDelayedSkipException(delay: Long = 0, task: () -> Unit): Runnable {
        return Handler(Looper.getMainLooper()).postDelayed(delay) {
            try {
                task()
            } catch (e: Exception) {
                e.printStackTrace()
                Logger.d("Handler-PostDelay-Exception: ${e.message}")
            }
        }
    }

    private fun openPlayStoreForRating(context: Context) {
        val uri = ("market://details?id=" + context.packageName).toUri()
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            context.startActivity(intent)
        } catch (e: Throwable) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                ("https://play.google.com/store/apps/details?id=" + context.packageName).toUri())
            )
        }
    }

    fun openChromeTab(context: Context, url: String) {
        try {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent: CustomTabsIntent = builder.build()
            customTabsIntent.launchUrl(
                context,
                url.toUri()
            )
        } catch (e: Throwable) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }
    }

    fun shareApp(context: Context, label: String) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, label)
            val shareMessage =
                "https://play.google.com/store/apps/details?id=${context.packageName}"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            context.startActivity(Intent.createChooser(shareIntent, label))
        } catch (e: Throwable) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }
    }

    fun applyMaskToBitmap(original: Bitmap, mask: Bitmap): Bitmap {
        val result = createBitmap(original.width, original.height)
        val canvas = Canvas(result)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawBitmap(original, 0f, 0f, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvas.drawBitmap(mask, 0f, 0f, paint)
        paint.xfermode = null

        return result
    }
}