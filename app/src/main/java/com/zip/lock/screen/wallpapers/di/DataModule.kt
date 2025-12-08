package com.zip.lock.screen.wallpapers.di

import com.zip.lock.screen.wallpapers.data.repository.CloudRepository
import com.zip.lock.screen.wallpapers.data.repository.CloudRepositoryImpl
import com.zip.lock.screen.wallpapers.data.repository.MediaRepository
import com.zip.lock.screen.wallpapers.data.repository.MediaRepositoryImpl
import com.zip.lock.screen.wallpapers.data.repository.VideoRepository
import com.zip.lock.screen.wallpapers.data.repository.VideoRepositoryImpl
import com.zip.lock.screen.wallpapers.data.source.local.database.dao.VideoDao
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindCloudRepository(
        impl: CloudRepositoryImpl
    ): CloudRepository

    @Binds
    @Singleton
    abstract fun bindMediaRepository(
        impl: MediaRepositoryImpl
    ): MediaRepository

    @Binds
    @Singleton
    abstract fun bindVideoRepository(
        impl: VideoRepositoryImpl
    ): VideoRepository

}