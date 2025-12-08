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
    val shimmerDrawable = if (useShimmer) createShimmerPlaceholder() else null
    if (url == null) return
    Glide.with(context).load(url).apply(
        if (useShimmer) {
            RequestOptions().placeholder(shimmerDrawable)
                .diskCacheStrategy(if (noCache) DiskCacheStrategy.NONE else DiskCacheStrategy.ALL)
                .also { requestOptions ->
                    val transformations = mutableListOf<Transformation<Bitmap>>()
                    if (centerCrop)
                        transformations.add(CenterCrop())
                    if (cornerRadiusDp > 0) {
                        val radiusInPixel = context.dpToPx(cornerRadiusDp)
                        transformations.add(RoundedCorners(radiusInPixel))
                    }
                    if (transformations.isNotEmpty())
                        requestOptions.transform(*transformations.toTypedArray())
                }
        } else {
            RequestOptions().placeholder(placeholder)
                .diskCacheStrategy(if (noCache) DiskCacheStrategy.NONE else DiskCacheStrategy.ALL)
                .also { requestOptions ->
                    val transformations = mutableListOf<Transformation<Bitmap>>()
                    if (centerCrop)
                        transformations.add(CenterCrop())
                    if (cornerRadiusDp > 0) {
                        val radiusInPixel = context.dpToPx(cornerRadiusDp)
                        transformations.add(RoundedCorners(radiusInPixel))
                    }
                    if (transformations.isNotEmpty())
                        requestOptions.transform(*transformations.toTypedArray())
                }
        }
    ).into(this)
}

fun createShimmerPlaceholder(): ShimmerDrawable {
    val shimmer = Shimmer.AlphaHighlightBuilder()
        .setDuration(1000)
        .setBaseAlpha(0.7f)
        .setHighlightAlpha(1.0f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()

    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }
    return shimmerDrawable
}

fun ImageView.setTintColorRes(context: Context, colorResId: Int) {
    this.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorResId))
}


