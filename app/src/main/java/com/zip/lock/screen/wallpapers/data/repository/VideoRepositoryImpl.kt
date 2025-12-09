package com.zip.lock.screen.wallpapers.data.repository

import androidx.lifecycle.LiveData
import com.zip.lock.screen.wallpapers.data.source.local.database.AppDatabase
import com.zip.lock.screen.wallpapers.data.source.local.database.dao.VideoDao
import com.zip.lock.screen.wallpapers.data.source.local.database.enitities.VideoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface VideoRepository {
    suspend fun insert(entity: VideoEntity)
    suspend fun update(entity: VideoEntity)
    suspend fun getAll(): List<VideoEntity>
    suspend fun insertOrUpdateByPath(entity: VideoEntity)
    suspend fun delete(entity: VideoEntity)
    suspend fun deleteByPath(path: String)
    fun getAllFlow(): Flow<List<VideoEntity>>
}

class VideoRepositoryImpl @Inject constructor(
    private val appDB: AppDatabase
): VideoRepository {

    override suspend fun getAll(): List<VideoEntity> = appDB.videoDao().getAllEntity()

    override fun getAllFlow(): Flow<List<VideoEntity>> = appDB.videoDao().getAllEntityFlow()

    override suspend fun insert(entity: VideoEntity) = appDB.videoDao().insert(entity)

    override suspend fun update(entity: VideoEntity) = appDB.videoDao().update(entity)

    override suspend fun insertOrUpdateByPath(entity: VideoEntity) {
        appDB.videoDao().insertOrUpdateByPath(entity)
    }

    override suspend fun delete(entity: VideoEntity) = appDB.videoDao().delete(entity)

    override suspend fun deleteByPath(path: String) {
        appDB.videoDao().deleteByPath(path)
    }
}