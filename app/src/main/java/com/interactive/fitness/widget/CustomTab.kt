package com.interactive.fitness.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.interactive.fitness.ext.visibleOrGone
import com.interactive.fitness.R
import com.interactive.fitness.databinding.ItemTabBinding

class CustomTab @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val mBinding = ItemTabBinding.inflate(LayoutInflater.from(context), this, true)

    fun bindData(title: String) {
        mBinding.tvTab.text = title
    }

    fun setItemSelect(isSelect: Boolean) {
        val typeface = if (isSelect) {
            ResourcesCompat.getFont(context, R.font.inter_semi_bold)
        } else {
            ResourcesCompat.getFont(context, R.font.inter_regular)
        }
        mBinding.tvTab.typeface = typeface
        mBinding.tvTab.isSelected = isSelect
        mBinding.icLine.visibleOrGone(isSelect)
    }
}