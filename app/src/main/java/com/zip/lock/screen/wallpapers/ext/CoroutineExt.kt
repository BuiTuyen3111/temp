package com.zip.lock.screen.wallpapers.ext

import kotlin.coroutines.suspendCoroutine

suspend fun <T : Any> getValue(provider: () -> T): T =
    suspendCoroutine { cont ->
        cont.resumeWith(Result.runCatching { provider() })
    }
