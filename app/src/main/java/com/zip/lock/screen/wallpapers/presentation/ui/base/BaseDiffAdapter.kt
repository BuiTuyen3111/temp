package com.zip.lock.screen.wallpapers.presentation.ui.base

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

abstract class BaseDiffAdapter<T, VH : ViewHolder, VB : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createVH(initViewBinding(parent, viewType))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindData(holder, getItem(position), position)
    }

    abstract fun initViewBinding(parent: ViewGroup, viewType: Int): VB
    abstract fun createVH(viewBinding: VB): VH
    abstract fun onBindData(holder: VH, item: T, pos: Int)
}