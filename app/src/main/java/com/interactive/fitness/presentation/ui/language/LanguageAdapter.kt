package com.interactive.fitness.presentation.ui.language

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.interactive.fitness.domain.model.LanguageModel
import com.interactive.fitness.presentation.ui.base.BaseAdapter
import com.interactive.fitness.utils.safeOnClickListener
import com.interactive.fitness.databinding.ItemLanguageBinding

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

    fun setSelectedPosition(pos: Int) {
        if (pos > -1) {
            if (this.pos == pos) return
            this.pos = pos

            notifyItemChanged(this.pos, LanguageModel::class.java)
        }
    }

    inner class ViewHolder(val binding: ItemLanguageBinding) : RecyclerView.ViewHolder(binding.root)
}