package com.interactive.fitness.presentation.ui.dialog

import android.view.Gravity
import com.interactive.fitness.ads.AdPlacement
import com.interactive.fitness.presentation.ui.base.BaseDialogFragment
import com.interactive.fitness.utils.safeOnClickListener
import com.leansoft.ads.AdManager
import com.interactive.fitness.databinding.DialogExitAppBinding

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