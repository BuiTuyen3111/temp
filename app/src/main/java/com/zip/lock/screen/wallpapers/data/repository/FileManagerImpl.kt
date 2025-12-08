package com.zip.lock.screen.wallpapers.data.repository

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import javax.inject.Inject

interface FileManager {

    /** Download file từ URL → Cache folder (returns File) */
    suspend fun downloadToCache(context: Context, url: String, fileName: String): File?

    /** Copy file sang thư viện (Gallery) */
    suspend fun copyFileToGallery(
        context: Context,
        sourceUri: Uri,
        fileName: String,
        mimeType: String = "application/octet-stream",
        subFolder: String = Environment.DIRECTORY_DOWNLOADS
    ): Uri?

    /** Download trực tiếp → Gallery */
    suspend fun downloadToGallery(
        context: Context,
        url: String,
        fileName: String,
        mimeType: String,
        subFolder: String = Environment.DIRECTORY_DOWNLOADS
    ): Uri?

    /** Extract .zip → target folder */
    suspend fun extractZip(zipFile: File, targetDir: File): Boolean

    /** Delete file an toàn */
    suspend fun deleteFile(file: File): Boolean
}

class FileManagerImpl @Inject constructor() : FileManager {

    override suspend fun downloadToCache(context: Context, url: String, fileName: String): File? =
        withContext(Dispatchers.IO) {
            try {
                val cacheFile = File(context.cacheDir, fileName)
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.connect()

                if (connection.responseCode != HttpURLConnection.HTTP_OK) return@withContext null

                val input = connection.inputStream
                val output = FileOutputStream(cacheFile)

                input.copyTo(output)

                output.close()
                input.close()

                cacheFile
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    /**
     * Copy a file (from File or Uri) into Gallery/Downloads/Pictures.
     */
    override suspend fun copyFileToGallery(
        context: Context,
        sourceUri: Uri,
        fileName: String,
        mimeType: String,
        subFolder: String
    ): Uri? = withContext(Dispatchers.IO) {

            val resolver = context.contentResolver

            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000)
                put(MediaStore.MediaColumns.RELATIVE_PATH, subFolder)
            }

            val collection =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                } else {
                    MediaStore.Files.getContentUri("external")
                }

            val uri = resolver.insert(collection, values) ?: return@withContext null

            try {
                resolver.openInputStream(sourceUri).use { input ->
                    resolver.openOutputStream(uri).use { output ->
                        if (input == null || output == null) return@withContext null
                        input.copyTo(output)
                    }
                }
                return@withContext uri
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }

    /**
     * Download a file from URL and save to Gallery.
     */
    override suspend fun downloadToGallery(
        context: Context,
        url: String,
        fileName: String,
        mimeType: String,
        subFolder: String
    ): Uri? = withContext(Dispatchers.IO) {

        val resolver = context.contentResolver

        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000)
            put(MediaStore.MediaColumns.RELATIVE_PATH, subFolder)
        }

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } else {
                MediaStore.Files.getContentUri("external")
            }

        val uri = resolver.insert(collection, values) ?: return@withContext null

        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.connect()

            resolver.openOutputStream(uri).use { output ->
                connection.inputStream.use { input ->
                    if (input == null || output == null) return@withContext null
                    input.copyTo(output)
                }
            }

            connection.disconnect()
            return@withContext uri
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }

    override suspend fun extractZip(zipFile: File, targetDir: File): Boolean =
        withContext(Dispatchers.IO) {
            try {
                if (!targetDir.exists()) targetDir.mkdirs()

                ZipInputStream(BufferedInputStream(FileInputStream(zipFile))).use { zis ->
                    var entry: ZipEntry?
                    val buffer = ByteArray(4096)

                    while (zis.nextEntry.also { entry = it } != null) {
                        val outFile = File(targetDir, entry!!.name)

                        // Folder → create & continue
                        if (entry.isDirectory) {
                            outFile.mkdirs()
                            continue
                        }

                        // File → extract
                        BufferedOutputStream(FileOutputStream(outFile)).use { bos ->
                            var count: Int
                            while (zis.read(buffer).also { count = it } != -1) {
                                bos.write(buffer, 0, count)
                            }
                        }
                    }
                }

                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

    override suspend fun deleteFile(file: File): Boolean =
        withContext(Dispatchers.IO) {
            try {
                if (file.exists()) file.delete() else false
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
}