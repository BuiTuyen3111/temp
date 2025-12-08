package com.zip.lock.screen.wallpapers.presentation.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zip.lock.screen.wallpapers.data.repository.CloudRepository
import com.zip.lock.screen.wallpapers.data.source.local.database.enitities.VideoEntity
import com.zip.lock.screen.wallpapers.data.source.remote.cloud.dto.CategoryData
import com.zip.lock.screen.wallpapers.data.source.remote.cloud.dto.CategoryItem
import com.zip.lock.screen.wallpapers.data.source.remote.cloud.dto.VideoDto
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    var repository: CloudRepository
) : BaseVM() {

    private val _videos = MutableLiveData<List<CategoryItem>>(emptyList())
    val videos: LiveData<List<CategoryItem>> get() = _videos
    var map = emptyMap<String, List<VideoEntity>>()

    fun getAllData(context: Context) {

        // Chỉ load 1 lần
        if (_videos.value!!.isNotEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.loadCategories(context)
            map = list.associate { it.name to it.videos.map { it.toVideoEntity() } }
            _videos.postValue(list)
        }
    }

    companion object {
        const val TAG = "HomeVM"
    }
}