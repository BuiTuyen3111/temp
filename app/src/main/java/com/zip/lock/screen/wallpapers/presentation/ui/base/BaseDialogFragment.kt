package com.zip.lock.screen.wallpapers.presentation.ui.base

import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.zip.lock.screen.wallpapers.App
import com.zip.lock.screen.wallpapers.R
import kotlinx.coroutines.launch

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {

    abstract var gravity: Int
    abstract var canceledOnTouchOutside: Boolean
    abstract var cancelBackPress: Boolean
    abstract var fullScreen: Boolean
    open var marginInDp: Int = 0
    open var withMatch: Boolean = false
    open var hasAnim: Boolean = true

    private var mDialog: Dialog? = null

    lateinit var mBinding: VB
    val TAG = javaClass.simpleName
    val appSession get() = App.instance.session
    val pref get() = App.instance.pref

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mDialog = BaseDialog(requireContext(), theme)
        (mDialog as BaseDialog).canBackPress = cancelBackPress
        mDialog?.setCanceledOnTouchOutside(canceledOnTouchOutside)
        mDialog?.window?.apply {
            setGravity(gravity)
            setBackgroundDrawableResource(android.R.color.transparent)
            if (hasAnim) attributes.windowAnimations = R.style.DialogAnimation
        }
        return mDialog!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mBinding = initViewBinding()
        return mBinding.root
    }

    protected abstract fun initViewBinding(): VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        mDialog?.window?.apply {
            if (withMatch) {
                setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            } else {
                val marginInPx = (marginInDp * context.resources.displayMetrics.density).toInt()
                val screenWidth = Resources.getSystem().displayMetrics.widthPixels
                val dialogWidth = screenWidth - (marginInPx * 2)
                if (fullScreen) {
                    setLayout(
                        dialogWidth, LayoutParams.MATCH_PARENT
                    )
                } else {
                    setLayout(
                        if (marginInPx == 0) LayoutParams.WRAP_CONTENT else dialogWidth,
                        LayoutParams.WRAP_CONTENT,
                    )
                }
            }
            hideSystemBars(decorView)
        }
    }

    private fun hideSystemBars(view: View) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            view.windowInsetsController?.apply {
                hide(WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION") view.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    abstract fun init()

    override fun show(manager: FragmentManager, tag: String?) {
        if (manager.isDestroyed || manager.isStateSaved) return
        super.show(manager, tag)
    }

    fun safeDismiss() {
        if (dialog?.isShowing == true && isAdded) {
            dismiss()
        }
    }

    internal class BaseDialog(context: Context, themeId: Int) : Dialog(context, themeId) {

        var canBackPress = true

        override fun onBackPressed() {
            if (!canBackPress) return
            super.onBackPressed()
        }
    }
}