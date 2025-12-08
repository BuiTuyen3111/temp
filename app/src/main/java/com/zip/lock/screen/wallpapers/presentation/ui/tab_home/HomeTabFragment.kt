package com.zip.lock.screen.wallpapers.presentation.ui.tab_home

import android.util.Log
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.zip.lock.screen.wallpapers.databinding.FragmentHomeTabBinding
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseFragment
import com.zip.lock.screen.wallpapers.presentation.ui.base.NothingViewModel
import com.zip.lock.screen.wallpapers.presentation.ui.home.HomeVM
import com.zip.lock.screen.wallpapers.presentation.ui.tab_favorite.FavoriteAdapter
import com.zip.lock.screen.wallpapers.widget.CustomTabHome
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeTabFragment : BaseFragment<FragmentHomeTabBinding, NothingViewModel>() {

    private var adapter: FavoriteAdapter? = null
    private var tabAdapter: TabAdapter? = null

    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initViewBinding(): FragmentHomeTabBinding {
        return FragmentHomeTabBinding.inflate(layoutInflater)
    }

    override fun initView() {
        adapter = FavoriteAdapter({

        }, {

        })
        mBinding.rcvHome.adapter = adapter

        tabAdapter = TabAdapter {
            Log.d(TAG, "initView datassss: ${homeVM.map[it] ?: emptyList()}")
            adapter?.setItems(homeVM.map[it] ?: emptyList())
        }
        mBinding.rcvTab.adapter = tabAdapter

        fetchData()
        observer()
    }

    private fun observer() {
        homeVM.videos.observe(viewLifecycleOwner) { videos ->
            val data = videos.map { it.name }
            Log.d(TAG, "initView ----- videos: $videos")
            tabAdapter?.setItems(data)
        }
    }

    private fun fetchData() {
        homeVM.getAllData(requireContext())
    }
}