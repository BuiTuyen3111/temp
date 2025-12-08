package com.zip.lock.screen.wallpapers.presentation.ui.activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.provider.Settings
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import com.leansoft.ads.ui.activity.LeansoftSplashActivity
import com.zip.lock.screen.wallpapers.App
import com.zip.lock.screen.wallpapers.R
import com.zip.lock.screen.wallpapers.ads.AdSharedPreference
import com.zip.lock.screen.wallpapers.domain.model.ShortCut
import com.zip.lock.screen.wallpapers.domain.model.ShortCutAction
import com.zip.lock.screen.wallpapers.presentation.ui.language.LanguageImpl
import com.zip.lock.screen.wallpapers.utils.AppLanguageUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : LeansoftSplashActivity() {

    @Inject
    lateinit var adSharedPreference: AdSharedPreference

    private val pref get() = App.instance.pref

    override fun loadedRemoteConfig(isSuccess: Boolean) {
//        adSharedPreference.setupWithRemoteConfig(FirebaseRemoteConfig.getInstance())
        createShortcutUninstall()
    }

    private fun createShortcutUninstall() {
        initShortcut(ShortCut(
            action = ShortCutAction.WALLPAPER,
            title = "Live Wallpapers",
            id = "Live Wallpapers",
            shortLabel = "Live Wallpapers",
            longLabel = "Live Wallpapers",
            image = R.drawable.ic_wallpaper_shotcut
        ))
        initShortcut(ShortCut(
            action = ShortCutAction.ZIP,
            title = "Zip Lock Wallpapers",
            id = "Zip Lock Wallpapers",
            shortLabel = "Zip Lock Wallpapers",
            longLabel = "Zip Lock Wallpapers",
            image = R.drawable.ic_zip_shotcut
        ))
    }

    private fun initShortcut(act: ShortCut) {
        val intent = Intent(this, MainActivity::class.java).apply {
            action = act.action.name
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val shortcut = ShortcutInfoCompat.Builder(this, act.id)
            .setShortLabel(act.shortLabel)
            .setLongLabel(act.longLabel)
            .setIcon(IconCompat.createWithResource(this, act.image))
            .setIntent(Intent(intent))
            .build()
        ShortcutManagerCompat.pushDynamicShortcut(this, shortcut)
    }

    override fun finishOnboarding(bundle: Bundle) {
        when (bundle.getString("action")) {
            "uninstall" -> {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = "package:${App.Companion.instance.packageName}".toUri()
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            else -> {
                startActivity(Intent(this, HomeActivity::class.java).apply {
                    this.putExtras(bundle)
                })
                finish()
            }
        }
    }

    override fun setLanguage(languageCode: String) {
        val languageModel = LanguageImpl.Companion.getAllLanguageList().find { it.code == languageCode }
        languageModel?.let {
            AppLanguageUtils.setLanguageModel(
                this,
                it
            )
        }
        recreate()
    }

    override fun checkedUpdate(hasUpdate: Boolean) {
    }

    override fun attachBaseContext(newBase: Context) {
        val resources: Resources = newBase.resources
        val languageModel = AppLanguageUtils.getLanguageModel(newBase)
        val languageCode = languageModel?.code ?: "en"
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }
}