package com.zip.lock.screen.wallpapers.domain.model

enum class ShortCutAction {
    UNINSTALL, ZIP, WALLPAPER
}

data class ShortCut(
    val action: ShortCutAction,
    val title: String,
    val id: String,
    val shortLabel: String,
    val longLabel: String,
    val image: Int
)