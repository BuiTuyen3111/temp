package com.interactive.fitness.presentation.ui.tab_setting

import com.interactive.fitness.presentation.ui.activity.HomeActivity
import com.interactive.fitness.presentation.ui.base.BaseFragment
import com.interactive.fitness.presentation.ui.base.NothingViewModel
import com.interactive.fitness.utils.AppConstants
import com.interactive.fitness.utils.AppUtils
import com.leansoft.ads.AdManager
import com.interactive.fitness.R
import com.interactive.fitness.databinding.FragmentSettingBinding
import com.interactive.fitness.presentation.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingTabFragment : BaseFragment<FragmentSettingBinding, NothingViewModel>() {

    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initViewBinding(): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(layoutInflater)
    }

    override fun initView() {
        with(mBinding) {
            header.leftBtn.setOnClickListener {
                navigationViewModel.back()
            }
            btnTerm.setOnClickListener {
                AdManager.instance.adDisableByClicked = true
                AppUtils.openChromeTab(requireContext(), AppConstants.TERMS_URL)
            }
            btnPolicy.setOnClickListener {
                AdManager.instance.adDisableByClicked = true
                AppUtils.openChromeTab(requireContext(), AppConstants.POLICY_URL)
            }
            btnRating.setOnClickListener {
                AdManager.instance.adDisableByClicked = true
                (activity as? HomeActivity)?.showRattingApp(rateInSetting = true)
            }
            btnShare.setOnClickListener {
                AdManager.instance.adDisableByClicked = true
                AppUtils.shareApp(requireContext(), getString(R.string.app_name))
            }
            btnLanguage.setOnClickListener {
                navigationViewModel.navigate(HomeFragmentDirections.actionHomeFragmentToLanguageSelectedFragment())
            }
        }
    }
}