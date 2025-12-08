package com.zip.lock.screen.wallpapers.presentation.ui.language

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zip.lock.screen.wallpapers.R
import com.zip.lock.screen.wallpapers.databinding.ItemLanguageBinding
import com.zip.lock.screen.wallpapers.domain.model.LanguageModel
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseAdapter
import com.zip.lock.screen.wallpapers.utils.safeOnClickListener
import kotlin.code

class LanguageAdapter(
    private val onItemClick: (LanguageModel) -> Unit,
    var selectedLanguageCode: String = ""
) : BaseAdapter<LanguageModel, LanguageAdapter.ViewHolder, ItemLanguageBinding>() {
    private var pos = -1

    override fun initViewBinding(parent: ViewGroup, viewType: Int): ItemLanguageBinding {
        return ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindData(holder: ViewHolder, item: LanguageModel, position: Int) {
        holder.binding.apply {
            icFlag.setImageResource(item.flag)
            tvlag.text = item.display
            ivSelect.isSelected = item.code == selectedLanguageCode
            root.safeOnClickListener {
                if (pos == position) return@safeOnClickListener
                if (selectedLanguageCode.isNotEmpty()) {
                    updateLanguageSelected(item.code)
                }
                onItemClick.invoke(item)
            }
        }
    }

    override fun createVH(viewBinding: ItemLanguageBinding): ViewHolder {
        return ViewHolder(viewBinding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateLanguageSelected(code: String) {
        selectedLanguageCode = code
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLanguageBinding) : RecyclerView.ViewHolder(binding.root)
}