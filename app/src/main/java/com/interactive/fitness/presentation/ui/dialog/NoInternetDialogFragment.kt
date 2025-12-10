package com.interactive.fitness.presentation.ui.dialog

import android.content.Intent
import android.provider.Settings
import android.view.Gravity
import com.interactive.fitness.presentation.ui.base.BaseDialogFragment
import com.interactive.fitness.utils.safeOnClickListener
import com.leansoft.ads.AdManager
import com.interactive.fitness.databinding.DialogNoInternetBinding

class NoInternetDialogFragment : BaseDialogFragment<DialogNoInternetBinding>() {

    override var gravity = Gravity.CENTER
    override var fullScreen = false
    override var canceledOnTouchOutside = false
    override var cancelBackPress = false

    var reload: (() -> Unit)? = null

    override fun initViewBinding(): DialogNoInternetBinding {
        return DialogNoInternetBinding.inflate(layoutInflater)
    }

    override fun init() {
        mBinding.btnOk.safeOnClickListener {
            AdManager.instance.adDisableByClicked = true
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            startActivity(intent)
        }
    }
}