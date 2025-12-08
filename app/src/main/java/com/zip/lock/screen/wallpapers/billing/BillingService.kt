package com.zip.lock.screen.wallpapers.billing

import android.app.Activity
import android.content.Context
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.models.StoreProduct

interface BillingService {

    fun initialize(context: Context)

    fun initStoreProducts()

    fun getCustomerInfo(info: (CustomerInfo) -> Unit)


    fun getOfferings()

    /**
     * Lắng nghe các thay đổi thông tin người dùng (CustomerInfo)
     */
    fun setCustomerInfoListener(onUpdate: (CustomerInfo) -> Unit)


    /**
     *
     */
    fun getProduct(productId: String): StoreProduct?


    /**
     *
     */
    fun getProducts(
        productIds: List<String>,
        onError: (PurchasesError) -> Unit,
        onReceived: (List<StoreProduct>) -> Unit
    )


    /**
     *
     */
    fun purchaseProduct(
        activity: Activity,
        product: StoreProduct,
        onSuccess: (CustomerInfo) -> Unit,
        onError: () -> Unit
    )

    /**
     * Khôi phục các giao dịch đã mua trước đó
     */
    fun restorePurchases(onComplete: () -> Unit, onError: (String) -> Unit)


    /**
     * Đổi gói
     */
    fun changeProduct(
        activity: Activity,
        oldProduct: StoreProduct,
        newProduct: StoreProduct,
        onSuccess: () -> Unit,
        onError: () -> Unit
    )

    /**
     *
     */
    fun logIn(
        userId: String,
        onError: (error: PurchasesError) -> Unit = {},
        onSuccess: (customerInfo: CustomerInfo, created: Boolean) -> Unit = { _, _ -> }
    )

    /**
     *
     */
    fun logOut(
        onError: (error: PurchasesError) -> Unit = {},
        onSuccess: (customerInfo: CustomerInfo) -> Unit = {}
    )


    /**
     *
     */
    fun updateUserDetails(info: CustomerInfo)


    /**
     *
     */
    fun getStoreProducts() : List<StoreProduct>
}