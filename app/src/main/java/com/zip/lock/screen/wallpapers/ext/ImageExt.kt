package com.zip.lock.screen.wallpapers.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

@SuppressLint("CheckResult")
fun ImageView.loadImage(
    url: Any?,
    placeholder: Int = 0,
    noCache: Boolean = false,
    centerCrop: Boolean = true,
    cornerRadiusDp: Float = 0f,
    useShimmer: Boolean = false
) {
    if (url == null) return

    val requestOptions = RequestOptions()
        .diskCacheStrategy(if (noCache) DiskCacheStrategy.NONE else DiskCacheStrategy.ALL)
        .also { opts ->
            val transforms = ArrayList<Transformation<Bitmap>>(2)
            if (centerCrop) transforms.add(CenterCrop())
            if (cornerRadiusDp > 0) {
                val radiusPx = context.dpToPx(cornerRadiusDp)
                transforms.add(RoundedCorners(radiusPx))
            }
            if (transforms.isNotEmpty()) opts.transform(*transforms.toTypedArray())
        }

    val request = Glide.with(context)
        .load(url)
        .apply(requestOptions)

    if (useShimmer) {
        request.placeholder(shimmerPlaceholder)
    } else {
        request.placeholder(placeholder)
    }

    request.into(this)
}

private val shimmerPlaceholder: ShimmerDrawable by lazy {
    ShimmerDrawable().apply {
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(1000)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(1.0f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()
        setShimmer(shimmer)
    }
}


fun ImageView.setTintColorRes(context: Context, colorResId: Int) {
    this.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorResId))
}


