package com.zip.lock.screen.wallpapers.billing

import com.revenuecat.purchases.models.Price
import com.revenuecat.purchases.models.StoreProduct

fun List<StoreProduct>.toSubscriptions(): Map<BillingProduct.Product, StoreProduct>  {
    return associateByNotNull { it.toProductType() }
}

fun StoreProduct.toProductType(): BillingProduct.Product? {
    return BillingProduct.Product.from(this.sku)
}

val Price.amount: Double
    get() = amountMicros / 1_000_000.0

inline fun <T, K : Any> Iterable<T>.associateByNotNull(keySelector: (T) -> K?): Map<K, T> {
    val result = mutableMapOf<K, T>()
    for (element in this) {
        val key = keySelector(element)
        if (key != null) {
            result[key] = element
        }
    }
    return result
}