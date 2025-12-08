package com.zip.lock.screen.wallpapers.billing

object BillingProduct {

    val allProductIDs: List<String> by lazy {
        Product.entries.map { product -> product.id }
    }

    val productIds: ProductIDsData get() = BillingConfig.productIDsLocal

    enum class Product(val id: String) {
        WEEKLY(productIds.SUB_WEEKLY),
        YEARLY(productIds.SUB_YEARLY);

        companion object {
            fun from(productId: String): Product? = entries.firstOrNull { it.id == productId }
        }
    }
}

data class ProductIDsData(
    val SUB_WEEKLY: String,
    val SUB_YEARLY: String,
)

