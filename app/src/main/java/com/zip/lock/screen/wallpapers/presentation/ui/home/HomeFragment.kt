package com.zip.lock.screen.wallpapers.presentation.ui.home

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zip.lock.screen.wallpapers.databinding.FragmentHomeBinding
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseFragment
import com.zip.lock.screen.wallpapers.widget.CustomTabHome
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeVM>() {

    override fun getClassVM(): Class<HomeVM> {
        return HomeVM::class.java
    }

    override fun initViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val adapter = HomePagerAdapter(this)

        mBinding.viewPager.adapter = adapter
        mBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {})
        mBinding.viewPager.isUserInputEnabled = false

        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val view = tab?.customView as CustomTabHome
                view.setItemSelect(true)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val view = tab?.customView as CustomTabHome
                view.setItemSelect(false)
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, pos ->
            if (tab.customView == null) {
                val view = CustomTabHome(requireContext())
                tab.customView = view
                view.bindData(pos)
            }
        }.attach()

        observer()
    }

    private fun observer() {}

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
