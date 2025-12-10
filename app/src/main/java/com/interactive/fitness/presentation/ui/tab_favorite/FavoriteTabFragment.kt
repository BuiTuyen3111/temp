package com.interactive.fitness.presentation.ui.tab_favorite

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.interactive.fitness.ext.gone
import com.interactive.fitness.ext.visible
import com.interactive.fitness.presentation.ui.base.BaseFragment
import com.interactive.fitness.databinding.FragmentFavoriteTabBinding
import com.interactive.fitness.presentation.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteTabFragment : BaseFragment<FragmentFavoriteTabBinding, FavoriteVM>() {

    private var adapter: FavoriteAdapter? = null

    override fun getClassVM(): Class<FavoriteVM> {
        return FavoriteVM::class.java
    }

    override fun initViewBinding(): FragmentFavoriteTabBinding {
        return FragmentFavoriteTabBinding.inflate(layoutInflater)
    }

    override fun initView() {
        adapter = FavoriteAdapter (
            viewModel = homeVM,
            onItemClick = {
                navigationViewModel.navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment( "favorite", position = it))
            },
            onFavoriteClick = { video, isFavorite ->
                homeVM.toggleFavorite(video, isFavorite)
            }
        )
        mBinding.rcvVideo.adapter = adapter
        observer()
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeVM.favoriteList.collect { list ->
                    adapter?.setItems(list)
                    if (list.isEmpty()) {
                        mBinding.rcvVideo.gone()
                        mBinding.emptyView.visible()
                        mBinding.tvEmpty.visible()
                    } else {
                        mBinding.rcvVideo.visible()
                        mBinding.emptyView.gone()
                        mBinding.tvEmpty.gone()
                    }
                }
            }
        }
    }
}