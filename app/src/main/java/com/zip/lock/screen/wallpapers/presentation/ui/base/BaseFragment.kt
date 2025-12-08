package com.zip.lock.screen.wallpapers.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.leansoft.ads.AdManager
import com.leansoft.ads.enums.RatingResult
import com.leansoft.ads.view.NativeAdViewContainer
import com.vungle.ads.internal.model.Placement
import com.zip.lock.screen.wallpapers.App
import com.zip.lock.screen.wallpapers.ads.AdPlacement
import com.zip.lock.screen.wallpapers.presentation.ui.activity.NavigationViewModel
import com.zip.lock.screen.wallpapers.presentation.ui.dialog.ExitAppDialogFragment
import com.zip.lock.screen.wallpapers.presentation.ui.home.HomeFragment
import com.zip.lock.screen.wallpapers.presentation.ui.home.HomeVM
import com.zip.lock.screen.wallpapers.utils.AppLog
import com.zip.lock.screen.wallpapers.ext.findNavControllerSafely
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding, VM : BaseVM> : Fragment() {

    val TAG = javaClass.simpleName

    lateinit var mViewModel: VM
    private var _binding: VB? = null
    protected val mBinding get() = _binding!!

    open var hasBackPress = true

    val appSession get() = App.instance.session
    val pref get() = App.instance.pref
    val firebaseMgr get() = App.instance.firebaseMgr
    val homeVM: HomeVM by activityViewModels()
    val navigationViewModel: NavigationViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        AppLog.lifeCircle(TAG, "onAttach:")
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        AppLog.lifeCircle(TAG, "onCreateView:")
        _binding = initViewBinding()
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AppLog.lifeCircle(TAG, "onViewCreated:")
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this)[getClassVM()]
        mViewModel.homeVM = homeVM
        mViewModel.navigationVM = navigationViewModel
        initView()
    }

    override fun onStart() {
        AppLog.lifeCircle(TAG, "onStart:")
        super.onStart()
    }

    override fun onResume() {
        AppLog.lifeCircle(TAG, "onResume:")
        super.onResume()
        when (javaClass) {
            HomeFragment::class.java -> {}
            else -> {}
        }
        if (hasBackPress) handleBackPress()
    }

    override fun onPause() {
        AppLog.lifeCircle(TAG, "onPause:")
        super.onPause()
    }


    override fun onStop() {
        AppLog.lifeCircle(TAG, "onStop:")
        super.onStop()
    }

    override fun onDestroyView() {
        AppLog.lifeCircle(TAG, "onDestroyView:")
        _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        AppLog.lifeCircle(TAG, "onDestroy:")
        super.onDestroy()
    }

    override fun onDetach() {
        AppLog.lifeCircle(TAG, "onDetach:")
        super.onDetach()
    }

    protected abstract fun getClassVM(): Class<VM>
    protected abstract fun initViewBinding(): VB
    protected abstract fun initView()

    private fun handleBackPress() {
        val currentDest = getCurrentDestination()
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            when (currentDest) {
                "MainFragment" -> {
                    showDialogExit()
                }

                else -> {
                    findNavControllerSafely()?.navigateUp()
                }
            }
        }
    }

    fun getCurrentDestination(): String {
        val currentDestination = findNavControllerSafely()?.currentDestination
        return currentDestination?.label?.toString() ?: "Unknown"
    }

    fun onBackPress() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    fun showRate(rateInSetting: Boolean = false) {
        val showRate = (!pref.rate && !appSession.rateInSession) || rateInSetting
        if (!showRate) return
        AdManager.instance.showRatingApp(requireActivity().supportFragmentManager) { result ->
            when (result) {
                RatingResult.SUCCESSFULLY, RatingResult.FEEDBACK -> {
                    appSession.rateInSession = true
                    pref.rate = true
                }

                else -> {
                    appSession.rateInSession = true
                }
            }
        }
    }

    fun showDialogExit() {
        val dialog = ExitAppDialogFragment()
        activity?.supportFragmentManager?.let { dialog.show(it, dialog.TAG) }
    }
}