package com.interactive.fitness.di

import com.interactive.fitness.ads.AdConfigImpl
import com.interactive.fitness.ads.AdSharedPreference
import com.interactive.fitness.presentation.ui.intro.OnboardingImpl
import com.interactive.fitness.presentation.ui.language.LanguageImpl
import com.interactive.fitness.presentation.ui.splash.SplashImpl
import com.interactive.fitness.presentation.ui.uninstall.UninstallAppImpl
import com.leansoft.ads.AdConfig
import com.leansoft.ads.ui.language.LeansoftLanguageInterface
import com.leansoft.ads.ui.onboarding.LeansoftOnboardingInterface
import com.leansoft.ads.ui.splash.LeansoftSplashInterface
import com.leansoft.ads.ui.uninstall.LeansoftUninstallInterface
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
    fun provideAdConfig(adSharedPreference: AdSharedPreference): AdConfig =
        AdConfigImpl(adSharedPreference)

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