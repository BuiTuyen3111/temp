package com.zip.lock.screen.wallpapers.di

import com.zip.lock.screen.wallpapers.data.source.local.database.AppDatabase
import com.zip.lock.screen.wallpapers.data.source.local.database.migration.MIGRATION_1_2
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "database-name")
//            .addMigrations(MIGRATION_1_2)
            .build()
}