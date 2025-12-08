package com.zip.lock.screen.wallpapers.billing

import com.revenuecat.purchases.models.GoogleReplacementMode

object BillingConfig {
    const val GOOGLE_API_KEY = "goog_qGNFUmrRUToWWJoinLgotKOnWIk"

    var productIDsLocal: ProductIDsData = ProductIDsData(
        SUB_WEEKLY = "com.zip.lock.screen.wallpapers.yearly",
        SUB_YEARLY = "com.zip.lock.screen.wallpapers.weekly",
    )

    val SUBS_REPLACEMENT_MODE = GoogleReplacementMode.CHARGE_FULL_PRICE
}