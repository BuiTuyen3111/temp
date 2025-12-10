package com.interactive.fitness.di

import android.content.Context
import com.interactive.fitness.ads.AdSharedPreference
import com.interactive.fitness.billing.BillingImpl
import com.interactive.fitness.billing.BillingService
import com.interactive.fitness.data.repository.FileManager
import com.interactive.fitness.data.repository.FileManagerImpl
import com.interactive.fitness.data.source.local.pref.PreferenceHelper
import com.interactive.fitness.utils.NetworkUtils
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
    fun providePreferenceHelper(@ApplicationContext context: Context): PreferenceHelper =
        PreferenceHelper(context)

    @Provides
    @Singleton
    fun provideNetworkUtils(@ApplicationContext context: Context) = NetworkUtils(context)

    @Provides
    @Singleton
    fun provideAdSharedPreference(@ApplicationContext context: Context) =
        AdSharedPreference(context)

    @Provides
    @Singleton
    fun provideBillingService(): BillingService = BillingImpl()

    @Provides
    @Singleton
    fun provideFileManager(): FileManager = FileManagerImpl()

}