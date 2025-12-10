package com.interactive.fitness.data.source.local.session

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSession @Inject constructor() {
    var rateInSession: Boolean = false
}