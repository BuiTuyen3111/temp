package com.interactive.fitness.presentation.ui.dialog

import android.view.Gravity
import com.interactive.fitness.presentation.ui.base.BaseDialogFragment
import com.interactive.fitness.databinding.DialogLoadingBinding

class LoadingDialogFragment : BaseDialogFragment<DialogLoadingBinding>() {

    override var gravity = Gravity.CENTER
    override var fullScreen = false
    override var canceledOnTouchOutside = true
    override var cancelBackPress = true
    override var hasAnim = false

    var onClick: () -> Unit = {}

    override fun initViewBinding(): DialogLoadingBinding {
        return DialogLoadingBinding.inflate(layoutInflater)
    }

    override fun init() {

    }
}