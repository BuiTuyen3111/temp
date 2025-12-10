package com.interactive.fitness.presentation.ui.tab_home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.interactive.fitness.ext.Event
import com.interactive.fitness.presentation.ui.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeTabVM @Inject constructor() : BaseVM() {

    private val _category = MutableLiveData<Event<String?>>()
    val category: LiveData<Event<String?>> = _category

}