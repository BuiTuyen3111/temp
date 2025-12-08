package com.zip.lock.screen.wallpapers.ext

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun NavController.safeNavigate(id: NavDirections) {
    try {
        this.navigate(id)
    } catch (_: Exception) {
    }
}

fun Fragment.findNavControllerSafely(): NavController? {
    return if (isAdded) {
        findNavController()
    } else {
        null
    }
}

fun NavController.safeNavigate(id: Int, args: Bundle? = null) {
    try {
        this.navigate(id, args)
    } catch (_: Exception) {

    }
}