package com.zip.lock.screen.wallpapers.presentation.ui.dialog

import android.view.Gravity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.leansoft.ads.AdManager
import com.zip.lock.screen.wallpapers.ads.AdPlacement
import com.zip.lock.screen.wallpapers.databinding.DialogExitAppBinding
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseDialogFragment
import com.zip.lock.screen.wallpapers.utils.safeOnClickListener

class ExitAppDialogFragment : BaseDialogFragment<DialogExitAppBinding>() {

    override var gravity = Gravity.CENTER
    override var fullScreen = false
    override var canceledOnTouchOutside = false
    override var cancelBackPress = false

    override fun initViewBinding(): DialogExitAppBinding {
        return DialogExitAppBinding.inflate(layoutInflater)
    }

    override fun init() {
        mBinding.apply {
            btOk.safeOnClickListener {
                requireActivity().finish()
            }
            btLater.safeOnClickListener {
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        AdManager.instance.destroyNativeAd(AdPlacement.NATIVE_EXIT.key)
    }
}