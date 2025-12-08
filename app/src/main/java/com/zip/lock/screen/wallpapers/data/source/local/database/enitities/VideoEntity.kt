package com.zip.lock.screen.wallpapers.data.source.local.database.enitities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var path: String? = null,
    var title: String? = null,
    var thumb: String? = null,
    var viewCount: String? = null,
    var isFavorite: Boolean = false
)
