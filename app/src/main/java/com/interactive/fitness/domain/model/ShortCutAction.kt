package com.interactive.fitness.domain.model

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