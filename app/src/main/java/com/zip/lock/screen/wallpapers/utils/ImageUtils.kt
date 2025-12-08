package com.zip.lock.screen.wallpapers.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import androidx.core.graphics.scale

object ImageUtils {

    const val TAG = "RotateImage"

    suspend fun rotateImageIfRequired(context: Context, imageUri: Uri): Bitmap? {
        try {
            val imageRotation = getImageRotation(context.contentResolver.openInputStream(imageUri)!!)
            val bitmap: Bitmap?
            withContext(Dispatchers.Default) {
                bitmap = rotateBitmap(context.contentResolver.openInputStream(imageUri)!!, imageRotation)
            }
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Cant check Picture rotate degree: ${e.message}")
        }
        return null
    }

    private fun getImageRotation(inputStream: InputStream): Float {
        try {
            val exif = ExifInterface(inputStream)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )

            val rotationDegrees = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                else -> 0f
            }

            Log.d(TAG, "Rotate: $rotationDegrees Degree")
            return rotationDegrees
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Cant check Picture rotate degree: ${e.message}")
        }
        return 0f
    }


    private fun rotateBitmap(inputStream: InputStream, rotationDegrees: Float): Bitmap? {
        try {
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            if (rotationDegrees != 0f) {
                val matrix = Matrix()
                matrix.postRotate(rotationDegrees)
                val rotatedBitmap = Bitmap.createBitmap(
                    originalBitmap,
                    0, 0,
                    originalBitmap.width,
                    originalBitmap.height,
                    matrix,
                    true
                )
                originalBitmap.recycle()
                return rotatedBitmap
            }
            return originalBitmap
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Cant rotate: ${e.message}")
            return null
        }
    }

}


