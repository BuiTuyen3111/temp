package com.zip.lock.screen.wallpapers.ext

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import com.zip.lock.screen.wallpapers.R
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Fragment.collectFlow(
    flow: Flow<T>,
    collect: suspend (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collect)
        }
    }
}

fun Fragment.navigateLifecycleResume(
    navGraph: Int
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            findNavControllerSafely()?.safeNavigate(navGraph)
            // Cancel coroutine sau khi navigate
            this@launch.cancel()
        }
    }
}

fun Fragment.navigateLifecycleResume(
    navGraph: NavDirections
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            findNavControllerSafely()?.safeNavigate(navGraph)
            this@launch.cancel()
        }
    }
}

fun Fragment.addFragment(
    fragment: Fragment,
    containerId: Int,
    tag: String? = null,
    addToBackStack: Boolean = true
) {
    parentFragmentManager.beginTransaction().apply {
        add(containerId, fragment, tag)
        if (addToBackStack) addToBackStack(tag)
        commit()
    }
}

fun Fragment.replaceFragment(
    fragment: Fragment,
    containerId: Int,
    tag: String? = null,
    addToBackStack: Boolean = true
) {
    parentFragmentManager.beginTransaction().apply {
        replace(containerId, fragment, tag)
        if (addToBackStack) addToBackStack(tag)
        commit()
    }
}

fun Fragment.removeFragment(fragment: Fragment) {
    parentFragmentManager.beginTransaction().apply {
        remove(fragment)
        commit()
    }
}

fun FragmentActivity.addFragment(
    fragment: Fragment,
    containerId: Int,
    tag: String? = null,
    addToBackStack: Boolean = true
) {
    if (supportFragmentManager.isStateSaved) return
    supportFragmentManager.beginTransaction().apply {
        add(containerId, fragment, tag)
        if (addToBackStack) addToBackStack(tag)
        commit()
    }
}

fun FragmentActivity.removeFragmentByTag(
    tag: String,
    hasAnim: Boolean = true,
    animation: Int = R.anim.slide_out_bottom
) {
    val fragment = supportFragmentManager.findFragmentByTag(tag) ?: return
    if (!hasAnim) {
        supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
        return
    }
    val view = fragment.view ?: return
    val anim = AnimationUtils.loadAnimation(this, animation)
    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation?) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
        }
        override fun onAnimationStart(animation: Animation?) {}
        override fun onAnimationRepeat(animation: Animation?) {}
    })
    view.startAnimation(anim)
}