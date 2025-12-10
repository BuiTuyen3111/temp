package com.interactive.fitness.di

import com.interactive.fitness.data.repository.CloudRepository
import com.interactive.fitness.data.repository.CloudRepositoryImpl
import com.interactive.fitness.data.repository.MediaRepository
import com.interactive.fitness.data.repository.MediaRepositoryImpl
import com.interactive.fitness.data.repository.VideoRepository
import com.interactive.fitness.data.repository.VideoRepositoryImpl
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