package com.zip.lock.screen.wallpapers.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.zip.lock.screen.wallpapers.domain.model.LanguageModel

object AppLanguageUtils {
    private const val PREFS_NAME = "LanguagePrefs"
    private const val KEY_LANGUAGE = "language"
    private val gson = Gson()
    @Volatile
    private var sharedPreferences: SharedPreferences? = null
    private fun getSharedPrefs(context: Context): SharedPreferences {
        synchronized(this) {
            if (sharedPreferences == null) {
                sharedPreferences =
                    context.applicationContext
                        .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            }
            return sharedPreferences!!
        }
    }

    fun getLanguageModel(context: Context): LanguageModel? {
        try {
            val json = getSharedPrefs(context).getString(KEY_LANGUAGE, null) ?: return null
            return gson.fromJson(json, LanguageModel::class.java)
        } catch (e: Exception) {
            return null
        }
    }

    fun setLanguageModel(context: Context, language: LanguageModel) {
        val json = gson.toJson(language)
        getSharedPrefs(context).edit{
            putString(KEY_LANGUAGE, json)?.apply()
        }
    }
}