package com.zip.lock.screen.wallpapers.presentation.ui.tab_home

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zip.lock.screen.wallpapers.databinding.FragmentHomeTabBinding
import com.zip.lock.screen.wallpapers.ext.EventObserver
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseFragment
import com.zip.lock.screen.wallpapers.widget.CustomTab
import com.zip.lock.screen.wallpapers.widget.CustomTabHome
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeTabFragment : BaseFragment<FragmentHomeTabBinding, HomeTabVM>() {

    private var adapter: TabHomePagerAdapter? = null

    override fun getClassVM(): Class<HomeTabVM> {
        return HomeTabVM::class.java
    }

    override fun initViewBinding(): FragmentHomeTabBinding {
        return FragmentHomeTabBinding.inflate(layoutInflater)
    }

    override fun initView() {
        fetchData()
        observer()
        mBinding.vpHome.isUserInputEnabled = false
        mBinding.tabHome.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val view = tab?.customView as CustomTab
                view.setItemSelect(true)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val view = tab?.customView as CustomTab
                view.setItemSelect(false)
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun observer() {
        homeVM.videos.observe(viewLifecycleOwner) { videos ->
            val data = videos.map { it.name }
            adapter = TabHomePagerAdapter(this, videos)
            mBinding.vpHome.adapter = adapter
            TabLayoutMediator(mBinding.tabHome, mBinding.vpHome) { tab, pos ->
                if (tab.customView == null) {
                    val view = CustomTab(requireContext())
                    tab.customView = view
                    view.bindData(data[pos])
                }
            }.attach()
        }
    }

    private fun fetchData() {
        homeVM.getAllData(requireContext())
    }
}