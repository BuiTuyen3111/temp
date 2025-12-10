package com.interactive.fitness.presentation.test

import com.interactive.fitness.presentation.ui.base.BaseFragment
import com.interactive.fitness.presentation.ui.base.NothingViewModel
import com.interactive.fitness.databinding.FragmentTestBinding
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