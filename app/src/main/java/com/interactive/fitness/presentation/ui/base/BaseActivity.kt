package com.interactive.fitness.presentation.ui.base

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.interactive.fitness.App

abstract class BaseActivity<VB: ViewBinding, VM: ViewModel> : AppCompatActivity() {

    val TAG = javaClass.simpleName

    private var _binding: VB? = null
    protected val mBinding get() = _binding!!

    lateinit var mViewModel: VM

    val appSession get() = App.Companion.instance.session
    val networkUtils get() = App.Companion.instance.networkUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = initViewBinding()
        setContentView(_binding!!.root)
        mViewModel = ViewModelProvider(this)[getClassVM()]
        initView()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    protected abstract fun getClassVM(): Class<VM>
    protected abstract fun initViewBinding(): VB
    protected abstract fun initView()
}