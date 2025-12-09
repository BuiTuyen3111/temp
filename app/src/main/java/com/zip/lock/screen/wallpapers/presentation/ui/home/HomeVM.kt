package com.zip.lock.screen.wallpapers.presentation.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zip.lock.screen.wallpapers.data.repository.CloudRepository
import com.zip.lock.screen.wallpapers.data.repository.VideoRepository
import com.zip.lock.screen.wallpapers.data.source.local.database.enitities.VideoEntity
import com.zip.lock.screen.wallpapers.data.source.remote.cloud.dto.CategoryItem
import com.zip.lock.screen.wallpapers.ext.Event
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val repository: CloudRepository,
    private val videoRepository: VideoRepository
) : BaseVM() {
    private val _videos = MutableLiveData<List<CategoryItem>>(emptyList())
    val videos: LiveData<List<CategoryItem>> = _videos
    private val favoriteCache = mutableSetOf<String>()
    private val _favoriteList = MutableStateFlow<List<VideoEntity>>(emptyList())
    val favoriteList = _favoriteList

    private val _homeTabSelected = MutableLiveData(false)
    val homeTabSelected: LiveData<Boolean> = _homeTabSelected

    var map = emptyMap<String, List<VideoEntity>>()

    private val toggleJobs = mutableMapOf<String, Job>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            videoRepository.getAllFlow().collect { list ->
                _favoriteList.value = list
                favoriteCache.clear()
                favoriteCache.addAll(list.map { it.path })
            }
        }
    }

    fun updateHomeTabSelected() {
        _homeTabSelected.postValue(true)
    }

    fun isFavorite(path: String): Boolean {
        return favoriteCache.contains(path)
    }

    fun toggleFavorite(video: VideoEntity, isFavorite: Boolean) {
        val path = video.path

        toggleJobs[path]?.cancel()

        val job = viewModelScope.launch(Dispatchers.IO) {
            delay(200)
            if (favoriteCache.contains(path) && !isFavorite) {
                videoRepository.deleteByPath(path)
            }
            if (!favoriteCache.contains(path) && isFavorite) {
                videoRepository.insert(video)
            }
        }

        toggleJobs[path] = job
    }

    fun getAllData(context: Context) {
        if (_videos.value!!.isNotEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.loadCategories(context)

            map = list.associate { category ->
                category.name to category.videos.map { it.toVideoEntity() }
            }
            _videos.postValue(list)
        }
    }
}
