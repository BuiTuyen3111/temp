package com.interactive.fitness.presentation.ui.tab_home

import com.interactive.fitness.data.source.remote.cloud.dto.CategoryItem
import com.interactive.fitness.presentation.ui.base.BaseFragment
import com.interactive.fitness.presentation.ui.base.NothingViewModel
import com.interactive.fitness.presentation.ui.tab_favorite.FavoriteAdapter
import com.interactive.fitness.databinding.FragmentListBinding
import com.interactive.fitness.presentation.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment: BaseFragment<FragmentListBinding, NothingViewModel>() {

    private var categoryItem: CategoryItem? = null
    private var adapter: FavoriteAdapter? = null

    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initViewBinding(): FragmentListBinding {
        return FragmentListBinding.inflate(layoutInflater)
    }

    override fun initView() {
        adapter = FavoriteAdapter(
            viewModel = homeVM,
            onItemClick = {
                navigationViewModel.navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        category = categoryItem?.name ?: "",
                        position = it
                    )
                )
            },
            onFavoriteClick = { video, isFavorite ->
                homeVM.toggleFavorite(video, isFavorite)
            }
        )
        homeVM.homeTabSelected.observe(viewLifecycleOwner) {
            adapter?.setItems(categoryItem?.videos?.map { it.toVideoEntity() } ?: emptyList())
        }
        mBinding.rcvHome.adapter = adapter
        adapter?.setItems(categoryItem?.videos?.map { it.toVideoEntity() } ?: emptyList())
    }

    companion object {
        fun newInstance(categoryItem: CategoryItem): ListFragment {
            val fragment = ListFragment().apply {
                this.categoryItem = categoryItem
            }
            return fragment
        }
    }
}