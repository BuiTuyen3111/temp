package com.zip.lock.screen.wallpapers.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
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

    @Query("SELECT * FROM video WHERE path = :path LIMIT 1")
    suspend fun getByPath(path: String): VideoEntity?

    @Delete
    suspend fun delete(entity: VideoEntity)

    @Query("DELETE FROM video WHERE path = :path")
    suspend fun deleteByPath(path: String)

    suspend fun insertOrUpdateByPath(entity: VideoEntity) {
        if (entity.path.isNullOrEmpty()) return
        val old = getByPath(entity.path!!)
        if (old == null) {
            insert(entity)
        } else {
            val updated = entity.copy(id = old.id)
            update(updated)
        }
    }

}