package com.zip.lock.screen.wallpapers.presentation.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zip.lock.screen.wallpapers.presentation.ui.tab_favorite.FavoriteTabFragment
import com.zip.lock.screen.wallpapers.presentation.ui.tab_home.HomeTabFragment
import com.zip.lock.screen.wallpapers.presentation.ui.tab_setting.SettingTabFragment

class HomePagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    private val fragments = listOf(
        HomeTabFragment(),
        FavoriteTabFragment(),
        SettingTabFragment()
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

}