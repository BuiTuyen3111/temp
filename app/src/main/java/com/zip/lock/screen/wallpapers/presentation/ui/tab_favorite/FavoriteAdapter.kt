package com.zip.lock.screen.wallpapers.presentation.ui.tab_favorite

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.zip.lock.screen.wallpapers.R
import com.zip.lock.screen.wallpapers.data.source.local.database.enitities.VideoEntity
import com.zip.lock.screen.wallpapers.databinding.ItemHomeBinding
import com.zip.lock.screen.wallpapers.databinding.ItemVideoBinding
import com.zip.lock.screen.wallpapers.ext.loadImage
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseAdapter
import com.zip.lock.screen.wallpapers.presentation.ui.home.HomeVM
import com.zip.lock.screen.wallpapers.utils.safeOnClickListener

class FavoriteAdapter(
    private var viewModel: HomeVM,
    var onItemClick: (Int) -> Unit,
    var onFavoriteClick: (VideoEntity, Boolean) -> Unit
) : BaseAdapter<VideoEntity, FavoriteAdapter.ViewHolder, ItemHomeBinding>() {

    override fun initViewBinding(
        parent: ViewGroup,
        viewType: Int
    ): ItemHomeBinding {
        return ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun createVH(viewBinding: ItemHomeBinding): ViewHolder {
        return ViewHolder(viewBinding)
    }

    override fun onBindData(
        holder: ViewHolder,
        item: VideoEntity,
        pos: Int
    ) {
        holder.binding.apply {
            root.safeOnClickListener {
                onItemClick.invoke(pos)
            }
            tvTitle.text = item.title
            tvView.text = item.viewCount
            imgPreview.loadImage(item.path, cornerRadiusDp = 16f, useShimmer = true)
            imgPreview.outlineProvider = ViewOutlineProvider.BACKGROUND
            imgPreview.clipToOutline = true
            icLike.setImageResource(if (viewModel.isFavorite(item.path)) R.drawable.ic_liked else R.drawable.ic_unlike)
            icLike.safeOnClickListener {
                item.isFavorite = !item.isFavorite
                icLike.setImageResource(if (item.isFavorite) R.drawable.ic_liked else R.drawable.ic_unlike)
                onFavoriteClick.invoke(item, item.isFavorite)
            }
        }
    }

    inner class ViewHolder(var binding: ItemHomeBinding): RecyclerView.ViewHolder(binding.root)
}