package com.zip.lock.screen.wallpapers.presentation.ui.activity

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.zip.lock.screen.wallpapers.ext.Event

class NavigationViewModel : ViewModel() {

    private val _actionDestination = MutableLiveData<Event<ActionNavigate>>()
    val actionDestination: LiveData<Event<ActionNavigate>> = _actionDestination

    private val _naviDirection = MutableLiveData<Event<NavDirections>>()
    val naviDirection: LiveData<Event<NavDirections>> = _naviDirection

    private val _actionBack = MutableLiveData<Event<Unit>>()
    val actionBack: LiveData<Event<Unit>> = _actionBack

    private val _actionBackToFrag = MutableLiveData<Event<Int>>()
    val actionBackToFrag: LiveData<Event<Int>> = _actionBackToFrag

    private val _showPaywall = MutableLiveData<Event<Unit>>()
    val showPaywall: LiveData<Event<Unit>> = _showPaywall

    private val _showNativeFullEvent = MutableLiveData<Event<String>>()
    val showNativeFullEvent: LiveData<Event<String>> = _showNativeFullEvent

    fun showNativeFull(placement: String){
        _showNativeFullEvent.postValue(Event(placement))
    }


    fun back() {
        _actionBack.value = Event(Unit)
    }

    fun backFrg(destinationId: Int) {
        _actionBackToFrag.value = Event(destinationId)
    }

    fun navigate(destination: Int, bundle: Bundle? = null, navOptions: NavOptions? = null) {
        _actionDestination.value = Event(ActionNavigate(destination, bundle, navOptions))
    }

    fun navigate(naviDirections: NavDirections) {
        _naviDirection.value = Event(naviDirections)
    }

    fun navigate(action: ActionNavigate) {
        _actionDestination.value = Event(action)
    }

    fun navigatePaywall() {
        _showPaywall.value = Event(Unit)
    }
}

data class ActionNavigate(
    val destination: Int,
    val args: Bundle? = null,
    var navOptions: NavOptions? = null
)