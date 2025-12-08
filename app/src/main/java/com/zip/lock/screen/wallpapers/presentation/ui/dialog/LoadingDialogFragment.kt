package com.zip.lock.screen.wallpapers.presentation.ui.dialog

import android.view.Gravity
import com.zip.lock.screen.wallpapers.databinding.DialogLoadingBinding
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseDialogFragment

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