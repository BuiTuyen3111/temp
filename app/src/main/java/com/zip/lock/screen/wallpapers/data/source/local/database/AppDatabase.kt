package com.zip.lock.screen.wallpapers.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zip.lock.screen.wallpapers.data.source.local.database.dao.VideoDao
import com.zip.lock.screen.wallpapers.data.source.local.database.enitities.VideoEntity

@Database(
   entities = [VideoEntity::class],
   version = 1,
   exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
   abstract fun videoDao(): VideoDao
}