package com.zip.lock.screen.wallpapers.presentation.ui.activity

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.leansoft.ads.AdManager
import com.leansoft.ads.enums.AdType
import com.leansoft.ads.enums.RatingResult
import com.zip.lock.screen.wallpapers.R
import com.zip.lock.screen.wallpapers.ads.AdPlacement
import com.zip.lock.screen.wallpapers.ads.AdSharedPreference
import com.zip.lock.screen.wallpapers.billing.BillingService
import com.zip.lock.screen.wallpapers.data.source.local.pref.PreferenceHelper
import com.zip.lock.screen.wallpapers.data.source.remote.cloud.FirebaseMgr
import com.zip.lock.screen.wallpapers.databinding.ActivityMainBinding
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseActivity
import com.zip.lock.screen.wallpapers.presentation.ui.dialog.NoInternetDialogFragment
import com.zip.lock.screen.wallpapers.presentation.ui.home.HomeVM
import com.zip.lock.screen.wallpapers.utils.AppLanguageUtils
import com.zip.lock.screen.wallpapers.ext.EventObserver
import com.zip.lock.screen.wallpapers.ext.safeNavigate
import com.zip.lock.screen.wallpapers.ext.runTryCatch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityMainBinding, HomeVM>() {

    private val navViewModel: NavigationViewModel by viewModels()
    var onAdFinished: ((Boolean) -> Unit)? = null

    private var onAdFullScreenImpression =
        { placement: String, type: AdType, onAdFinished: (Boolean) -> Unit ->
            var check = false
            this.onAdFinished = onAdFinished
            if (type == AdType.REWARD || type == AdType.INTER_REWARD || type == AdType.INTER && placement != AdPlacement.INTER_SPLASH.key && placement != AdPlacement.INTER_SPLASH_BACKUP.key) {
                if (type == AdType.REWARD || type == AdType.INTER_REWARD) {
                    if (AdManager.instance.adEnablePlacement(AdPlacement.NATIVE_FULL_REWARD.key)) {
                        AdManager.instance.preloadNativeAd(AdPlacement.NATIVE_FULL_REWARD.key)
                        navViewModel.showNativeFull(AdPlacement.NATIVE_FULL_REWARD.key)
                        check = true
                    }
                } else {
                    if (AdManager.instance.adEnablePlacement(AdPlacement.NATIVE_FULL_INTER.key)) {
                        AdManager.instance.preloadNativeAd(AdPlacement.NATIVE_FULL_INTER.key)
                        navViewModel.showNativeFull(AdPlacement.NATIVE_FULL_INTER.key)
                    }
                }
            }
            check
        }

    private val viewModel: NavigationViewModel by viewModels()

    @Inject
    lateinit var pref: PreferenceHelper

    @Inject
    lateinit var firebaseMgr: FirebaseMgr

    @Inject
    lateinit var adSharedPreference: AdSharedPreference

    @Inject
    lateinit var billingService: BillingService

    private var navController: NavController? = null

    var dialog: NoInternetDialogFragment? = null

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getClassVM(): Class<HomeVM> {
        return HomeVM::class.java
    }

    override fun initView() {
        hideNavigationBar()
    }

    override fun onResume() {
        super.onResume()
        AdManager.instance.setAdFullScreenImpressionListener(onAdFullScreenImpression)
    }

    private fun hideNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.window.insetsController?.hide(WindowInsets.Type.navigationBars())
            this.window.insetsController?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            @Suppress("DEPRECATION")
            this.window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        viewModel.actionDestination.observe(this, EventObserver { action ->
            navController?.safeNavigate(action.destination)
        })

        viewModel.naviDirection.observe(this, EventObserver { action ->
            navController?.safeNavigate(action)
        })

        viewModel.actionBack.observe(this, EventObserver {
            AdManager.instance.showInterAd(AdPlacement.INTER_BACK.key) {
                navController?.popBackStack()
            }
        })

        viewModel.actionBackToFrag.observe(this, EventObserver {
            navController?.popBackStack(destinationId = it, inclusive = true)
        })

        navViewModel.showNativeFullEvent.observe(this, EventObserver {
            showDialogNativeFull(it)
        })

        val destination = intent.getIntExtra("NAV_DESTINATION", -1)
        if (destination != -1 && savedInstanceState == null) {
            navController?.safeNavigate(destination)
        }
        viewModel.showPaywall.observe(this, EventObserver {

        })
        registerNetwork()
    }

    private fun onAvailable() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                runTryCatch {
                    dialog?.dismiss()
                    dialog = null
                }
            }
        }
    }

    private fun onLost() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                runTryCatch {
                    showDialogInternet()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog = null
        networkUtils.network.removeObservers(this)
        networkUtils.stopListening()
    }

    override fun attachBaseContext(newBase: Context) {
        val resources: Resources = newBase.resources
        val languageModel = AppLanguageUtils.getLanguageModel(newBase)
        val languageCode = languageModel?.code ?: "en"
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        Locale.setDefault(locale)
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }

    private fun showDialogInternet() {
        if (dialog != null) return
        dialog = NoInternetDialogFragment()
        dialog?.show(supportFragmentManager, "NoInternetDialogFragment")
        dialog?.reload = {}
    }

    fun registerNetwork() {
        networkUtils.startListening()
        if (!networkUtils.isNetworkAvailable()) {
            onLost()
        }
        networkUtils.network.observe(this) { state ->
            if (state) {
                onAvailable()
            } else {
                onLost()
            }
        }
    }

    private fun showDialogNativeFull(placement: String) {
        val fragmentManager = supportFragmentManager
        val existingDialog = fragmentManager.findFragmentByTag("NativeFullDialogFragment")

        if (existingDialog == null || !existingDialog.isVisible) {
            AdManager.instance.showNativeFullDialog(fragmentManager, placement) {
                onAdFinished?.invoke(true)
                onAdFinished = null
            }
        }
    }

    fun showRattingApp(rateInSetting: Boolean = false) {
        if ((!pref.rate && !appSession.rateInSession) || rateInSetting) {
            AdManager.instance.showRatingApp(supportFragmentManager) { result ->
                when (result) {
                    RatingResult.SUCCESSFULLY, RatingResult.FEEDBACK  -> {
                        appSession.rateInSession = true
                        pref.rate = true
                        AdManager.instance.adDisableByClicked = false
                    }
                    else -> {
                        appSession.rateInSession = true
                        AdManager.instance.adDisableByClicked = false
                    }
                }
            }
        }
    }
}