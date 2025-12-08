package com.zip.lock.screen.wallpapers.presentation.ui.base

import com.zip.lock.screen.wallpapers.App
import com.zip.lock.screen.wallpapers.presentation.ui.activity.NavigationViewModel
import com.zip.lock.screen.wallpapers.presentation.ui.home.HomeVM
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

open class BaseVM: ViewModel() {
    lateinit var homeVM: HomeVM
    lateinit var navigationVM: NavigationViewModel
    val networkUtils get() = App.instance.networkUtils

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