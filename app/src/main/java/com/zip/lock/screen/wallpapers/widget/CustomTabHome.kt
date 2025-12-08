package com.zip.lock.screen.wallpapers.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.zip.lock.screen.wallpapers.R
import com.zip.lock.screen.wallpapers.databinding.ItemTabHomeBinding
import com.zip.lock.screen.wallpapers.ext.visibleOrGone
import com.zip.lock.screen.wallpapers.ext.visibleOrInvisible

class CustomTabHome @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val mBinding = ItemTabHomeBinding.inflate(LayoutInflater.from(context), this, true)

    fun setItemSelect(isSelect: Boolean) {
        mBinding.iconTab.isSelected = isSelect
        mBinding.tvTab.visibleOrGone(isSelect)
        mBinding.bottomView.visibleOrInvisible(isSelect)
    }

    fun bindData(pos: Int) {
        when (pos) {
            0 -> {
                mBinding.iconTab.setImageResource(R.drawable.ic_tab_home_selector)
                mBinding.tvTab.text = context.resources.getString(R.string.msg_home)
            }
            1 -> {
                mBinding.iconTab.setImageResource(R.drawable.ic_tab_favorite_selector)
                mBinding.tvTab.text = context.resources.getString(R.string.msg_favorite)
            }
            2 -> {
                mBinding.iconTab.setImageResource(R.drawable.ic_tab_setting_selector)
                mBinding.tvTab.text = context.resources.getString(R.string.msg_setting)
            }
        }
    }
}