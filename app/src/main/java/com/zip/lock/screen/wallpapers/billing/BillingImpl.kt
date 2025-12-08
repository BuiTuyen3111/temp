package com.zip.lock.screen.wallpapers.billing

import android.app.Activity
import android.content.Context
import android.util.Log
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.getOfferingsWith
import com.revenuecat.purchases.interfaces.GetStoreProductsCallback
import com.revenuecat.purchases.interfaces.LogInCallback
import com.revenuecat.purchases.interfaces.PurchaseCallback
import com.revenuecat.purchases.interfaces.ReceiveCustomerInfoCallback
import com.revenuecat.purchases.interfaces.UpdatedCustomerInfoListener
import com.revenuecat.purchases.models.StoreProduct
import com.revenuecat.purchases.models.StoreTransaction
import javax.inject.Inject

class BillingImpl @Inject constructor() : BillingService {

    private var storeProducts: List<StoreProduct> = listOf()
    private var customerInfo: CustomerInfo? = null

    override fun initialize(context: Context) {
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(
            PurchasesConfiguration.Builder(
                context,
                BillingConfig.GOOGLE_API_KEY
            ).build()
        )
        initStoreProducts()
        Log.d(TAG, "RevenueCat initialized")
    }

    override fun getStoreProducts(): List<StoreProduct> {
        return storeProducts
    }

    override fun getCustomerInfo(info: (CustomerInfo) -> Unit) {
        Purchases.sharedInstance.getCustomerInfo(
            callback = object : ReceiveCustomerInfoCallback {
            override fun onError(error: PurchasesError) {

            }

            override fun onReceived(customerInfo: CustomerInfo) {
                this@BillingImpl.customerInfo = customerInfo
                info.invoke(customerInfo)
                Log.d(TAG, "onReceived: $customerInfo")
            }
        })
    }

    override fun initStoreProducts() {
        getProducts(
            BillingProduct.allProductIDs,
            onError = {},
            onReceived = { allStoreProducts ->
                storeProducts = allStoreProducts
                Log.d(TAG, "price: ${storeProducts.map { it.price }}")
                Log.d(TAG, "name: ${storeProducts.map { it.name }}")
                Log.d(TAG, "title: ${storeProducts.map { it.title }}")
                Log.d(TAG, "description: ${storeProducts.map { it.description }}")
            }
        )
    }

    override fun setCustomerInfoListener(onUpdate: (CustomerInfo) -> Unit) {
        Purchases.sharedInstance.updatedCustomerInfoListener =
            UpdatedCustomerInfoListener { customerInfo ->
                Log.d(TAG, "CustomerInfo updated: ${customerInfo}")
                onUpdate(customerInfo)
            }
    }

    override fun getProduct(productId: String): StoreProduct? {
        return storeProducts.firstOrNull {
            it.id.startsWith(productId)
        }?.apply { Log.d(TAG, "StoreProduct: $id == productId:$productId") }
    }

    override fun getProducts(
        productIds: List<String>,
        onError: (PurchasesError) -> Unit,
        onReceived: (List<StoreProduct>) -> Unit
    ) {
        Log.d(TAG, "getProducts: $productIds")
//        getOfferings()
        Purchases.sharedInstance.getProducts(
            productIds, object : GetStoreProductsCallback {
                override fun onError(error: PurchasesError) {
                    Log.d(TAG, "onError: ${error.message}")
                    onError(error)
                }

                override fun onReceived(storeProducts: List<StoreProduct>) {
                    Log.d(TAG, "ok storeProducts: ${storeProducts}")
                    onReceived(storeProducts)
                }
            })
    }

    override fun purchaseProduct(
        activity: Activity,
        product: StoreProduct,
        onSuccess: (CustomerInfo) -> Unit,
        onError: () -> Unit
    ) {
        if (storeProducts.isEmpty()) initStoreProducts()
        Purchases.sharedInstance.purchase(
            purchaseParams = PurchaseParams.Builder(activity, product).build(),
            callback = object : PurchaseCallback {
                override fun onCompleted(
                    storeTransaction: StoreTransaction, customerInfo: CustomerInfo
                ) {
                    this@BillingImpl.customerInfo = customerInfo
                    onSuccess.invoke(customerInfo)
                    Log.d(TAG, "onCompleted: storeTransaction $storeTransaction")
                    Log.d(TAG, "onCompleted: customerInfo $customerInfo")
                }

                override fun onError(
                    error: PurchasesError, userCancelled: Boolean
                ) {
                    Log.d(TAG, "onError: $error")
                }
            })
    }

    override fun restorePurchases(
        onComplete: () -> Unit,
        onError: (String) -> Unit
    ) {
        Purchases.sharedInstance.restorePurchases(callback = object : ReceiveCustomerInfoCallback {
            override fun onError(error: PurchasesError) {

            }

            override fun onReceived(customerInfo: CustomerInfo) {
                this@BillingImpl.customerInfo = customerInfo
            }
        })
    }

    override fun updateUserDetails(info: CustomerInfo) {

    }

    override fun changeProduct(
        activity: Activity,
        oldProduct: StoreProduct,
        newProduct: StoreProduct,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        Purchases.sharedInstance.purchase(
            purchaseParams = PurchaseParams.Builder(activity, newProduct)
                .oldProductId(oldProduct.id)
                .googleReplacementMode(BillingConfig.SUBS_REPLACEMENT_MODE)
                .build(),
            callback = object : PurchaseCallback {
                override fun onCompleted(
                    storeTransaction: StoreTransaction,
                    customerInfo: CustomerInfo
                ) {
                    this@BillingImpl.customerInfo = customerInfo
                }

                override fun onError(error: PurchasesError, userCancelled: Boolean) {

                }

            }
        )
    }

    override fun logIn(
        userId: String,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo, created: Boolean) -> Unit
    ) {
        Purchases.sharedInstance.logIn(userId, object : LogInCallback {
            override fun onError(error: PurchasesError) {
                onError(error)
            }

            override fun onReceived(customerInfo: CustomerInfo, created: Boolean) {
                this@BillingImpl.customerInfo = customerInfo
                onSuccess(customerInfo, created)
            }
        })
    }

    override fun logOut(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo) -> Unit
    ) {
        Purchases.sharedInstance.logOut(
            callback = object : ReceiveCustomerInfoCallback {
                override fun onError(error: PurchasesError) {
                    onError.invoke(error)
                }

                override fun onReceived(customerInfo: CustomerInfo) {
                    this@BillingImpl.customerInfo = customerInfo
                    onSuccess.invoke(customerInfo)
                }
            }
        )
    }

    override fun getOfferings() {
        Purchases.sharedInstance.getOfferingsWith(onSuccess = { offerings ->
            val currentOffering = offerings.current
            if (currentOffering == null) {
                Log.d(TAG, "getOfferings: $currentOffering")
                return@getOfferingsWith
            }
            
        }, onError = { error ->
            Log.d(TAG, "getOfferings: $error")
        })
    }

    companion object {
        private const val TAG = "BillingManager"
    }
}