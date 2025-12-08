package com.zip.lock.screen.wallpapers.ext

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import androidx.core.graphics.scale
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.math.min

fun Bitmap.toFile(
    file: File,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    quality: Int = 90,
): File {
    FileOutputStream(file).use { out ->
        this.compress(format, quality, out)
    }
    return file
}

fun Bitmap.toUri(context: Context, fileName: String = "image_${System.currentTimeMillis()}.jpg"): Uri? {
    val values = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp")
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) ?: return null

    resolver.openOutputStream(uri)?.use { out ->
        compress(Bitmap.CompressFormat.JPEG, 90, out)
    }

    return uri
}

fun Bitmap.toByteArray(
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
    quality: Int = 100
): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(format, quality, stream)
    return stream.toByteArray()
}

fun Bitmap.toInputStream(): InputStream {
    return ByteArrayInputStream(toByteArray())
}

fun Bitmap.resize(maxWidth: Int, maxHeight: Int): Bitmap {
    val ratio = min(maxWidth / width.toFloat(), maxHeight / height.toFloat())
    val newW = (width * ratio).toInt()
    val newH = (height * ratio).toInt()
    return this.scale(newW, newH)
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.copyOptimized(): Bitmap =
    copy(Bitmap.Config.ARGB_8888, true)


