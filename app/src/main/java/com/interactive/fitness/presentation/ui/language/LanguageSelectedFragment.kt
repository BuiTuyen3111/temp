package com.interactive.fitness.presentation.ui.language

import android.content.Intent
import com.interactive.fitness.ext.visible
import com.interactive.fitness.presentation.ui.activity.HomeActivity
import com.interactive.fitness.presentation.ui.base.BaseFragment
import com.interactive.fitness.utils.AppLanguageUtils
import com.interactive.fitness.utils.safeOnClickListener
import com.interactive.fitness.databinding.FragmentLanguageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageSelectedFragment : BaseFragment<FragmentLanguageBinding, LanguageViewModel>() {

    override var hasBackPress = false

    override fun getClassVM(): Class<LanguageViewModel> {
        return LanguageViewModel::class.java
    }

    override fun initViewBinding(): FragmentLanguageBinding {
        return FragmentLanguageBinding.inflate(layoutInflater)
    }

    override fun initView() {
        mBinding.btnNext.visible()
        val adapter = LanguageAdapter(onItemClick = { item ->
            mViewModel.setLanguageModel(item)
        }, AppLanguageUtils.getLanguageModel(requireContext())?.code ?: "en")
        adapter.setItems(LanguageImpl.Companion.getAllLanguageList())

        AppLanguageUtils.getLanguageModel(requireContext())?.let {
            mViewModel.setLanguageModel(it)
        }
        mBinding.recyclerView.adapter = adapter
        mBinding.btnNext.safeOnClickListener {
            if (mViewModel.getLanguageModel() == null) return@safeOnClickListener
            nextScreen()
        }
    }

    private fun nextScreen() {
        restartApp()
    }

    private fun restartApp() {
        AppLanguageUtils.setLanguageModel(
            requireContext(),
            mViewModel.getLanguageModel()!!
        )
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}