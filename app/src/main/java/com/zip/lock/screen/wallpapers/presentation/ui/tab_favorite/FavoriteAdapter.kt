package com.zip.lock.screen.wallpapers.presentation.ui.tab_favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zip.lock.screen.wallpapers.R
import com.zip.lock.screen.wallpapers.data.source.local.database.enitities.VideoEntity
import com.zip.lock.screen.wallpapers.databinding.ItemVideoBinding
import com.zip.lock.screen.wallpapers.ext.loadImage
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseAdapter
import com.zip.lock.screen.wallpapers.utils.safeOnClickListener

class FavoriteAdapter(
    var onItemClick: (VideoEntity) -> Unit,
    var onFavoriteClick: (VideoEntity) -> Unit
) : BaseAdapter<VideoEntity, FavoriteAdapter.ViewHolder, ItemVideoBinding>() {

    override fun initViewBinding(
        parent: ViewGroup,
        viewType: Int
    ): ItemVideoBinding {
        return ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun createVH(viewBinding: ItemVideoBinding): ViewHolder {
        return ViewHolder(viewBinding)
    }

    override fun onBindData(
        holder: ViewHolder,
        item: VideoEntity,
        pos: Int
    ) {
        holder.binding.apply {
            root.safeOnClickListener {
                onItemClick.invoke(item)
            }
            tvTitle.text = item.title
            tvView.text = item.viewCount
            imgPreview.loadImage(item.path ?: "", cornerRadiusDp = 16f, useShimmer = true)
            icLike.setImageResource(if (item.isFavorite) R.drawable.ic_liked else R.drawable.ic_unlike)
            icLike.safeOnClickListener {
                item.isFavorite = !item.isFavorite
                icLike.setImageResource(if (item.isFavorite) R.drawable.ic_liked else R.drawable.ic_unlike)
                onFavoriteClick.invoke(item)
            }
        }
    }


    inner class ViewHolder(var binding: ItemVideoBinding): RecyclerView.ViewHolder(binding.root)
}