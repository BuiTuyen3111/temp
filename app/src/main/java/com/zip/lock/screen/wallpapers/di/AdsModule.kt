package com.zip.lock.screen.wallpapers.di

import com.leansoft.ads.AdConfig
import com.leansoft.ads.ui.language.LeansoftLanguageInterface
import com.leansoft.ads.ui.onboarding.LeansoftOnboardingInterface
import com.leansoft.ads.ui.splash.LeansoftSplashInterface
import com.leansoft.ads.ui.uninstall.LeansoftUninstallInterface
import com.zip.lock.screen.wallpapers.ads.AdConfigImpl
import com.zip.lock.screen.wallpapers.ads.AdSharedPreference
import com.zip.lock.screen.wallpapers.presentation.ui.intro.OnboardingImpl
import com.zip.lock.screen.wallpapers.presentation.ui.language.LanguageImpl
import com.zip.lock.screen.wallpapers.presentation.ui.splash.SplashImpl
import com.zip.lock.screen.wallpapers.presentation.ui.uninstall.UninstallAppImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdsModule {

    @Provides
    @Singleton
    fun provideAdConfig(adSharedPreference: AdSharedPreference): AdConfig = AdConfigImpl(adSharedPreference)

    @Provides
    @Singleton
    fun provideSplashInterface(): LeansoftSplashInterface = SplashImpl()

    @Provides
    @Singleton
    fun provideLanguageInterface(): LeansoftLanguageInterface = LanguageImpl()

    @Provides
    @Singleton
    fun provideOnboardingInterface(): LeansoftOnboardingInterface = OnboardingImpl()

    @Provides
    @Singleton
    fun provideUninstallInterface(): LeansoftUninstallInterface = UninstallAppImpl()
}