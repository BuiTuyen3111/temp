package com.interactive.fitness.presentation.ui.tab_home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.interactive.fitness.data.source.remote.cloud.dto.CategoryItem

class TabHomePagerAdapter(
    fragment: Fragment,
    private val categoryItems: List<CategoryItem>
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = categoryItems.size

    override fun createFragment(position: Int): Fragment {
        return ListFragment.newInstance(categoryItems[position])
    }

}