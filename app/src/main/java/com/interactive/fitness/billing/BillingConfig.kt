package com.interactive.fitness.billing

import com.revenuecat.purchases.models.GoogleReplacementMode

object BillingConfig {
    const val GOOGLE_API_KEY = "goog_qGNFUmrRUToWWJoinLgotKOnWIk"

    var productIDsLocal: ProductIDsData = ProductIDsData(
        SUB_WEEKLY = "com.interactive.fitness.yearly",
        SUB_YEARLY = "com.interactive.fitness.weekly",
    )

    val SUBS_REPLACEMENT_MODE = GoogleReplacementMode.CHARGE_FULL_PRICE
}