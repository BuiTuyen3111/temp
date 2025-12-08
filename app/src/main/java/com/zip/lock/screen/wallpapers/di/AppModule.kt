package com.zip.lock.screen.wallpapers.di

import android.content.Context
import com.leansoft.ads.AdConfig
import com.leansoft.ads.ui.language.LeansoftLanguageInterface
import com.leansoft.ads.ui.onboarding.LeansoftOnboardingInterface
import com.leansoft.ads.ui.splash.LeansoftSplashInterface
import com.leansoft.ads.ui.uninstall.LeansoftUninstallInterface
import com.zip.lock.screen.wallpapers.ads.AdConfigImpl
import com.zip.lock.screen.wallpapers.ads.AdSharedPreference
import com.zip.lock.screen.wallpapers.billing.BillingImpl
import com.zip.lock.screen.wallpapers.billing.BillingService
import com.zip.lock.screen.wallpapers.data.repository.FileManager
import com.zip.lock.screen.wallpapers.data.repository.FileManagerImpl
import com.zip.lock.screen.wallpapers.data.source.local.pref.PreferenceHelper
import com.zip.lock.screen.wallpapers.presentation.ui.intro.OnboardingImpl
import com.zip.lock.screen.wallpapers.presentation.ui.language.LanguageImpl
import com.zip.lock.screen.wallpapers.presentation.ui.splash.SplashImpl
import com.zip.lock.screen.wallpapers.presentation.ui.uninstall.UninstallAppImpl
import com.zip.lock.screen.wallpapers.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferenceHelper(@ApplicationContext context: Context): PreferenceHelper = PreferenceHelper(context)

    @Provides
    @Singleton
    fun provideNetworkUtils(@ApplicationContext context: Context) = NetworkUtils(context)

    @Provides
    @Singleton
    fun provideAdSharedPreference(@ApplicationContext context: Context) = AdSharedPreference(context)

    @Provides
    @Singleton
    fun provideBillingService(): BillingService = BillingImpl()

    @Provides
    @Singleton
    fun provideFileManager(): FileManager = FileManagerImpl()

}