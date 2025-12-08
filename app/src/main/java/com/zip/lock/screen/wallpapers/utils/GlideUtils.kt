package com.zip.lock.screen.wallpapers.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.zip.lock.screen.wallpapers.App
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.coroutines.resume
import androidx.core.graphics.createBitmap

object GlideUtils {

    private val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .skipMemoryCache(false)

    private fun ctx(context: Context) = context.applicationContext

    /**
     * Preload để cache image vào memory + disk
     */
    fun preload(
        context: Context,
        url: Any
    ) {
        Glide.with(ctx(context))
            .load(url)
            .apply(requestOptions)
            .preload()
    }

    /**
     * Preload list ảnh
     */
    fun preloadList(
        context: Context,
        urls: List<Any>
    ) {
        val glide = Glide.with(ctx(context))
        urls.forEach { url ->
            glide
                .load(url)
                .apply(requestOptions)
                .preload()
        }
    }

    /**
     * Suspend function lấy Bitmap từ Glide
     * -> chạy tối ưu, đảm bảo không block UI
     */
    suspend fun getBitmap(
        context: Context,
        model: Any,
        width: Int = Target.SIZE_ORIGINAL,
        height: Int = Target.SIZE_ORIGINAL
    ): Bitmap = suspendCancellableCoroutine { cont ->

        Glide.with(ctx(context))
            .asBitmap()
            .load(model)
            .apply(requestOptions)
            .into(object : CustomTarget<Bitmap>(width, height) {

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    if (cont.isActive) cont.resume(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    if (cont.isActive)
                        cont.resume(createBitmap(1, 1))
                }
            })

        cont.invokeOnCancellation {}
    }
}
