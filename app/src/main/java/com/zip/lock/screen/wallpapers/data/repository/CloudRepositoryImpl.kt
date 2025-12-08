package com.zip.lock.screen.wallpapers.data.repository

import android.content.Context
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.zip.lock.screen.wallpapers.data.source.remote.cloud.FirebaseMgr
import com.zip.lock.screen.wallpapers.data.source.remote.cloud.dto.CategoryItem
import com.zip.lock.screen.wallpapers.data.source.remote.cloud.dto.RootDto
import com.zip.lock.screen.wallpapers.data.source.remote.cloud.dto.VideoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CloudRepository {
    suspend fun getData(): List<VideoDto>
    suspend fun loadCategories(context: Context): List<CategoryItem>
}

class CloudRepositoryImpl @Inject constructor(
    private val firebaseMgr: FirebaseMgr
): CloudRepository {

    override suspend fun getData(): List<VideoDto> {
        return emptyList()
    }

    override suspend fun loadCategories(context: Context): List<CategoryItem> = withContext(Dispatchers.IO) {
        val json = context.assets.open("data.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<RootDto>() {}.type
        val root = Gson().fromJson<RootDto>(json, type)

        root.toCategoryList()
    }
}