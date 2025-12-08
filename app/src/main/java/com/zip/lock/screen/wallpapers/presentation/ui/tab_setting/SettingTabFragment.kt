package com.zip.lock.screen.wallpapers.presentation.ui.tab_setting

import com.leansoft.ads.AdManager
import com.zip.lock.screen.wallpapers.R
import com.zip.lock.screen.wallpapers.databinding.FragmentSettingBinding
import com.zip.lock.screen.wallpapers.presentation.ui.activity.HomeActivity
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseFragment
import com.zip.lock.screen.wallpapers.presentation.ui.base.NothingViewModel
import com.zip.lock.screen.wallpapers.presentation.ui.home.HomeFragmentDirections
import com.zip.lock.screen.wallpapers.utils.AppConstants
import com.zip.lock.screen.wallpapers.utils.AppUtils
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