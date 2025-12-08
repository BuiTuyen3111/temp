package com.zip.lock.screen.wallpapers.utils

import android.util.Log

object AppLog {

    private const val LF_TAG = "LIFE_CIRCLE_LOG"
    private const val API_TAG = "API_LOG"
    private const val ADS_TAG = "ADS_LOG"

    fun lifeCircle(tag: String, msg: String) {
        Log.i(LF_TAG, "$tag: $msg")
    }

    fun apiInfo(tag: String, msg: String) {
        Log.i(API_TAG, "$tag: $msg")
    }

    fun adsInfo(tag: String, msg: String) {
        Log.i(ADS_TAG, "$tag: $msg")
    }
}