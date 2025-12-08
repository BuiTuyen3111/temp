package com.zip.lock.screen.wallpapers.presentation.ui.tab_home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zip.lock.screen.wallpapers.databinding.ItemTabBinding
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseAdapter
import com.zip.lock.screen.wallpapers.utils.safeOnClickListener
import kotlin.collections.forEach

class TabAdapter(
    private var onClick: (String) -> Unit
) : BaseAdapter<String, TabAdapter.AlbumViewHolder, ItemTabBinding>() {

    inner class AlbumViewHolder(var binding: ItemTabBinding) : RecyclerView.ViewHolder(binding.root)

    override fun initViewBinding(parent: ViewGroup, viewType: Int): ItemTabBinding {
        return ItemTabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindData(holder: AlbumViewHolder, item: String, pos: Int) {
        holder.binding.apply {
            tvTab.text = item
            root.safeOnClickListener {
                onClick.invoke(item)
            }
        }
    }

    override fun createVH(viewBinding: ItemTabBinding): AlbumViewHolder {
        return AlbumViewHolder(viewBinding)
    }
}