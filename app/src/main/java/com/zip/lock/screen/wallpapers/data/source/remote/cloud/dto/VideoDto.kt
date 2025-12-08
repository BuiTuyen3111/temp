package com.zip.lock.screen.wallpapers.data.source.remote.cloud.dto

import com.zip.lock.screen.wallpapers.data.source.local.database.enitities.VideoEntity

data class VideoDto(
    var title: String,
    val viewCount: String,
    val thumb: String,
    val videoPath: String
) {
    fun toVideoEntity(): VideoEntity {
        return VideoEntity(
            title = title,
            viewCount = viewCount,
            thumb = thumb,
            path = videoPath
        )
    }
}

data class CategoryItem(
    val name: String,
    val videos: List<VideoDto>
)

data class CategoryData(
    val videos: List<VideoDto>
)

data class RootDto(
    val data: Map<String, CategoryData>
) {
    fun toCategoryList(): List<CategoryItem> {
        return data.map { entry ->
            CategoryItem(
                name = entry.key,
                videos = entry.value.videos
            )
        }
    }
}