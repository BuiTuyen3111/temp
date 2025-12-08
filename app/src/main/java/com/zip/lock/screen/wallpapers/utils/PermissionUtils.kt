package com.zip.lock.screen.wallpapers.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {

    private val permissions: Array<String>
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

    fun getRequestPermissions(): Array<String> {
        val permissions = permissions.toMutableList()
        return permissions.toTypedArray()
    }

    fun hasStoragePermission(context: Context): Boolean {
        val requestPermissions = getRequestPermissions()
        requestPermissions.forEach { permission ->
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    fun isPermissionPermanentlyDenied(activity: Activity, permission: String): Boolean {
        return !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) &&
                ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED
    }
}