package com.zip.lock.screen.wallpapers.presentation.ui.tab_home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zip.lock.screen.wallpapers.data.source.remote.cloud.dto.CategoryItem
import com.zip.lock.screen.wallpapers.ext.Event
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeTabVM @Inject constructor() : BaseVM() {

    private val _category = MutableLiveData<Event<String?>>()
    val category: LiveData<Event<String?>> = _category

}