package com.zip.lock.screen.wallpapers.utils

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.ExoDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache

@UnstableApi
object VideoCache {
    private var simpleCache: SimpleCache? = null

    fun getInstance(context: Context): SimpleCache {
        if (simpleCache == null) {
            val cacheSize: Long = 500L * 1024L * 1024L // 500MB
            val databaseProvider = ExoDatabaseProvider(context)
            val evictor = LeastRecentlyUsedCacheEvictor(cacheSize)
            simpleCache = SimpleCache(context.cacheDir, evictor, databaseProvider)
        }
        return simpleCache!!
    }
}
