package com.interactive.fitness.presentation.ui.base

import com.interactive.fitness.App
import com.interactive.fitness.presentation.ui.activity.NavigationViewModel
import com.interactive.fitness.presentation.ui.home.HomeVM
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseVM: ViewModel() {
    lateinit var homeVM: HomeVM
    lateinit var navigationVM: NavigationViewModel
    val networkUtils get() = App.Companion.instance.networkUtils

    fun <T> MutableLiveData<T>.runTryCatch(
        coroutineScope: CoroutineScope,
        block: suspend () -> T,
        exception: ((Exception) -> Unit)? = null
    ) {
        coroutineScope.launch {
            try {
                if (networkUtils.isNetworkAvailable()) {
                    val result = withContext(Dispatchers.IO) {
                        block()
                    }
                    postValue(result)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                exception?.invoke(e)
            }
        }
    }
}