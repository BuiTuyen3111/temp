package com.zip.lock.screen.wallpapers.presentation.ui.tab_favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.zip.lock.screen.wallpapers.data.repository.VideoRepository
import com.zip.lock.screen.wallpapers.data.repository.VideoRepositoryImpl
import com.zip.lock.screen.wallpapers.data.source.local.database.enitities.VideoEntity
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteVM @Inject constructor(
    private val repository: VideoRepository
) : BaseVM() {

    val videoListFlow: StateFlow<List<VideoEntity>> =
        repository.getAllFlow()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun insert(entity: VideoEntity) {
        viewModelScope.launch {
            repository.insert(entity)
        }
    }

    fun update(entity: VideoEntity) {
        viewModelScope.launch {
            repository.update(entity)
        }
    }
}
