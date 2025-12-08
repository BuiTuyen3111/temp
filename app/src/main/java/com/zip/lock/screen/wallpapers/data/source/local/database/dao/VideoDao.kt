package com.zip.lock.screen.wallpapers.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zip.lock.screen.wallpapers.data.source.local.database.enitities.VideoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: VideoEntity)

    @Update
    suspend fun update(entity: VideoEntity)

    @Query("SELECT * FROM video")
    fun getAllEntity(): List<VideoEntity>

    @Query("SELECT * FROM video")
    fun getAllEntityFlow(): Flow<List<VideoEntity>>
}