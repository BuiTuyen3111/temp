package com.zip.lock.screen.wallpapers.presentation.test

import com.zip.lock.screen.wallpapers.databinding.FragmentTestBinding
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseFragment
import com.zip.lock.screen.wallpapers.presentation.ui.base.NothingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestFragment : BaseFragment<FragmentTestBinding, NothingViewModel>() {

    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initViewBinding(): FragmentTestBinding {
        return FragmentTestBinding.inflate(layoutInflater)
    }

    override fun initView() {

    }

}