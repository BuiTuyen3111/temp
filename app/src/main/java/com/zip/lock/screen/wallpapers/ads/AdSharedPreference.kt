package com.zip.lock.screen.wallpapers.ads

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leansoft.ads.AdManager

class AdSharedPreference(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("ad_prefs", Context.MODE_PRIVATE)

    fun setAdUnitId(placement: String, adUnitId: String) {
        prefs.edit { putString("ad_unit_id_$placement", adUnitId) }
    }

    fun getAdUnitId(placement: String): String {
        var adUnitId: String = prefs.getString("ad_unit_id_$placement", "") ?: ""
        if (adUnitId.isEmpty()) {
            for (item in AdPlacement.entries) {
                if (item.key == placement) {
                    adUnitId = item.id
                }
            }
        }
        return adUnitId
    }

    fun setAdEnabled(placement: String, enabled: Boolean) {
        prefs.edit { putBoolean("ad_enabled_$placement", enabled) }
    }

    fun getAdEnabled(placement: String): Boolean {
        Log.d("getAdEnabled", ": $placement ${prefs.getBoolean("ad_enabled_$placement", true)}")
        return prefs.getBoolean("ad_enabled_$placement", true)
    }

    fun setInterInterval(interval: Int) {
        prefs.edit { putInt("inter_interval", interval) }
    }

    fun getInterInterval(): Int {
        return prefs.getInt("inter_interval", 20)
    }

    fun setAdsEnabled(enabled: Boolean) {
        prefs.edit { putBoolean("ads_enabled", enabled) }
    }

    fun setInterTimeout(timeout: Int) {
        prefs.edit { putInt("inter_timeout", timeout) }
    }

    fun getInterTimeout(): Int {
        return prefs.getInt("inter_timeout", 5)
    }

    fun setGenerateNoAdsCount(time: Int) {
        prefs.edit { putInt("generation_no_ads_count", time) }
    }

    fun getGenerateNoAdsCount(): Int {
        return prefs.getInt("generation_no_ads_count", 2)
    }

    fun setAppOpenTimeout(timeout: Int) {
        prefs.edit { putInt("app_open_timeout", timeout) }
    }

    fun getAppOpenTimeout(): Int {
        return prefs.getInt("app_open_timeout", 10)
    }

    fun setRewardedTimeout(timeout: Int) {
        prefs.edit { putInt("rewarded_timeout", timeout) }
    }

    fun getRewardedTimeout(): Int {
        return prefs.getInt("rewarded_timeout", 20)
    }

    fun setAlwaysPreloadNative(placement: String, count: Int) {
        prefs.edit { putInt("always_preload_native_$placement", count) }
    }

    fun getAlwaysPreloadNative(adUnitId: String): Int {
        return prefs.getInt("always_preload_native_$adUnitId", 0)
    }

    fun setAlwaysPreloadInter(adUnitId: String, enable: Boolean) {
        prefs.edit { putBoolean("always_preload_inter_$adUnitId", enable) }
    }

    fun getAlwaysPreloadInter(adUnitId: String): Boolean {
        return prefs.getBoolean("always_preload_inter_$adUnitId", false)
    }

    fun setCanUseOtherAdUnit(adUnitId: String, enable: Boolean) {
        prefs.edit { putBoolean("can_use_other_ad_unit_$adUnitId", enable) }
    }

    fun getCanUseOtherAdUnit(adUnitId: String): Boolean {
        return prefs.getBoolean("can_use_other_ad_unit_$adUnitId", false)
    }

    fun setupWithRemoteConfig(config: FirebaseRemoteConfig) {
        try {
            val gson = Gson()
            val adUnitId: Map<String, String> = gson.fromJson(
                config.getString("ad_unit_id"),
                object : TypeToken<Map<String, String>>() {}.type
            )
            val adEnabled: Map<String, Boolean> = gson.fromJson(
                config.getString("ad_placement_enable"),
                object : TypeToken<Map<String, Boolean>>() {}.type
            )
            val alwaysPreloadNative: Map<String, Int> = gson.fromJson(
                config.getString("always_preload_native"),
                object : TypeToken<Map<String, Int>>() {}.type
            )
            val alwaysPreloadInter: Map<String, Boolean> = gson.fromJson(
                config.getString("always_preload_inter"),
                object : TypeToken<Map<String, Boolean>>() {}.type
            )
            val canUseOtherAdUnit: Map<String, Boolean> = gson.fromJson(
                config.getString("can_use_other_ad_unit"),
                object : TypeToken<Map<String, Boolean>>() {}.type
            )
            for (item in adUnitId.entries) {
                setAdUnitId(item.key, item.value)
            }
            for (item in adEnabled.entries) {
                Log.d("setupWithRemoteConfig", ": $item")
                setAdEnabled(item.key, item.value)
            }
            for (item in alwaysPreloadNative.entries) {
                setAlwaysPreloadNative(item.key, item.value)
            }
            for (item in alwaysPreloadInter.entries) {
                setAlwaysPreloadInter(item.key, item.value)
            }
            for (item in canUseOtherAdUnit.entries) {
                setCanUseOtherAdUnit(item.key, item.value)
            }
            setInterTimeout(config.getLong("inter_timeout").toInt())
            setInterInterval(config.getLong("inter_intervals").toInt())
            setAdsEnabled(config.getBoolean("hien_qc"))
            setAppOpenTimeout(config.getLong("app_open_timeout").toInt())
            setRewardedTimeout(config.getLong("reward_timeout").toInt())

            //add devices test
            val devices = config.getString("devices_test")
            if (devices.isNotEmpty()) {
                AdManager.instance.setTestDeviceIDs(devices.split("\n"))
            }
        } catch (e: Exception) {

        }
    }
}
