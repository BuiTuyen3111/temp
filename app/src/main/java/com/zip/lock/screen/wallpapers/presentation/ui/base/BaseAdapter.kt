package com.zip.lock.screen.wallpapers.presentation.ui.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.zip.lock.screen.wallpapers.App

abstract class BaseAdapter<T, VH: ViewHolder, VB: ViewBinding>: RecyclerView.Adapter<VH>() {

    val pref get() = App.instance.pref
    var data = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createVH(initViewBinding(parent, viewType))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindData(holder, data[position], position)
    }

    override fun getItemCount(): Int = data.size

    abstract fun initViewBinding(parent: ViewGroup, viewType: Int): VB
    abstract fun createVH(viewBinding: VB): VH
    abstract fun onBindData(holder: VH, item: T, pos: Int)

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<T>) {
        data.clear()
        data.addAll(newItems)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(item: T) {
        data.clear()
        data.add(item)
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        data.add(item)
        notifyItemInserted(data.lastIndex)
    }

    fun addItemAt(position: Int, item: T) {
        val safePosition = position.coerceIn(0, data.size)
        data.add(safePosition, item)
        notifyItemInserted(safePosition)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItemNotifyData(item: T) {
        data.add(item)
        notifyDataSetChanged()
    }

    fun addItems(items: List<T>) {
        val start = data.size
        data.addAll(items)
        notifyItemRangeInserted(start, items.size)
    }

    fun updateItemByIndex(index: Int, item: T) {
        if (index in data.indices) {
            data[index] = item
            notifyItemChanged(index)
        }
    }

    fun removeItemByIndex(index: Int) {
        if (index in data.indices) {
            data.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun removeItem(item: T) {
        val index = data.indexOf(item)
        if (index != -1) {
            data.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun getItem(position: Int): T? {
        return data.getOrNull(position)
    }

    fun addItemAtPos(pos: Int, item: T) {
        data.add(pos, item)
        notifyItemInserted(pos)
    }

}