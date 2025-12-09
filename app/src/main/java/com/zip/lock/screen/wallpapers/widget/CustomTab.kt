package com.zip.lock.screen.wallpapers.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.zip.lock.screen.wallpapers.R
import com.zip.lock.screen.wallpapers.databinding.ItemTabBinding
import com.zip.lock.screen.wallpapers.databinding.ItemTabHomeBinding
import com.zip.lock.screen.wallpapers.ext.visibleOrGone
import com.zip.lock.screen.wallpapers.ext.visibleOrInvisible

class CustomTab @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val mBinding = ItemTabBinding.inflate(LayoutInflater.from(context), this, true)

    fun bindData(title: String) {
        mBinding.tvTab.text = title
    }

    fun setItemSelect(isSelect: Boolean) {
        val typeface = if (isSelect) {
            ResourcesCompat.getFont(context, R.font.poppins_semi_bold)
        } else {
            ResourcesCompat.getFont(context, R.font.poppins_regular)
        }
        mBinding.tvTab.typeface = typeface
        mBinding.tvTab.isSelected = isSelect
        mBinding.icLine.visibleOrGone(isSelect)
    }
}