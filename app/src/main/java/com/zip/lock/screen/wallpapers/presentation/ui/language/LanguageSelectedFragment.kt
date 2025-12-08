package com.zip.lock.screen.wallpapers.presentation.ui.language

import android.content.Intent
import com.leansoft.ads.AdManager
import com.zip.lock.screen.wallpapers.ads.AdPlacement
import com.zip.lock.screen.wallpapers.databinding.FragmentLanguageBinding
import com.zip.lock.screen.wallpapers.presentation.ui.activity.HomeActivity
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseFragment
import com.zip.lock.screen.wallpapers.presentation.ui.language.LanguageImpl.Companion.getAllLanguageList
import com.zip.lock.screen.wallpapers.utils.AppLanguageUtils
import com.zip.lock.screen.wallpapers.ext.findNavControllerSafely
import com.zip.lock.screen.wallpapers.utils.safeOnClickListener
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
        val adapter = LanguageAdapter(onItemClick = { item ->

        }, selectedLanguageCode = mViewModel.getSelectedLanguage()?.code ?: "en")
        adapter.setItems(getAllLanguageList())
        AppLanguageUtils.getLanguageModel(requireContext())?.let {
            mViewModel.setLanguageModel(it)
        }
        mBinding.recyclerView.adapter = adapter
        mBinding.btnBack.safeOnClickListener {
            findNavControllerSafely()?.navigateUp()
        }
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
        AdManager.instance.destroyNativeAd(AdPlacement.NATIVE_CLICK.key)
    }
}