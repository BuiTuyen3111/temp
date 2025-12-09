package com.zip.lock.screen.wallpapers.presentation.ui.tab_home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zip.lock.screen.wallpapers.data.source.remote.cloud.dto.CategoryItem
import com.zip.lock.screen.wallpapers.presentation.ui.tab_favorite.FavoriteTabFragment
import com.zip.lock.screen.wallpapers.presentation.ui.tab_home.HomeTabFragment
import com.zip.lock.screen.wallpapers.presentation.ui.tab_setting.SettingTabFragment

class TabHomePagerAdapter(
    fragment: Fragment,
    private val categoryItems: List<CategoryItem>
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = categoryItems.size

    override fun createFragment(position: Int): Fragment {
        return ListFragment.newInstance(categoryItems[position])
    }

}