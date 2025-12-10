package com.interactive.fitness.utils

import com.interactive.fitness.App
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AFInAppEventType
import com.appsflyer.AppsFlyerLib

object AppsflyerLogger {

    fun logPurchase(price: Double, currency: String = "USD") {
        val netRevenue = price * 0.8

        val eventValues = mapOf(
            AFInAppEventParameterName.REVENUE to netRevenue,
            AFInAppEventParameterName.CURRENCY to currency
        )

        logEvent(AFInAppEventType.PURCHASE, eventValues)
    }

    fun logEvent(
        eventName: String,
        eventValues: Map<String, Any>)
    {
        AppsFlyerLib.getInstance().logEvent(App.Companion.instance, eventName, eventValues)
    }
}