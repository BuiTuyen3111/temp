package com.zip.lock.screen.wallpapers.ext

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat

fun View.cancelTransition() {
    transitionName = null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.visibleOrGone(isShow: Boolean) {
    this.visibility = if (isShow) View.VISIBLE else View.GONE
}

fun View.visibleOrInvisible(isShow: Boolean) {
    this.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
}

fun TextView.setTextColorCompat(resId: Int) {
    setTextColor(ContextCompat.getColor(context, resId))
}

fun View.setPaddingDp(all: Int) {
    val px = (all * resources.displayMetrics.density).toInt()
    setPadding(px, px, px, px)
}
