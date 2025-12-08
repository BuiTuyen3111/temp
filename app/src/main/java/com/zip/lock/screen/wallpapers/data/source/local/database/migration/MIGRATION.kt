package com.zip.lock.screen.wallpapers.data.source.local.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Tạo bảng app_token
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS app_token (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                accessKey TEXT NOT NULL,
                secretKey TEXT NOT NULL,
                expired INTEGER NOT NULL
            )
            """.trimIndent()
        )

        // Tạo bảng upload_token
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS upload_token (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                token TEXT NOT NULL,
                fileName TEXT NOT NULL,
                expired INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }
}