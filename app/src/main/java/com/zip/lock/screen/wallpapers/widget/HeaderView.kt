package com.zip.lock.screen.wallpapers.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.zip.lock.screen.wallpapers.R
import com.zip.lock.screen.wallpapers.databinding.HeaderViewBinding

@SuppressLint("UseKtx")
class HeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binding = HeaderViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        attrs?.let {
            val a = context.obtainStyledAttributes(it, R.styleable.HeaderView)

            val title = a.getString(R.styleable.HeaderView_headerText)
            binding.tvTitle.text = title

            val gravity = a.getInt(R.styleable.HeaderView_headerTextGravity, 1)
            binding.tvTitle.gravity = when (gravity) {
                0 -> Gravity.START
                1 -> Gravity.CENTER
                2 -> Gravity.END
                else -> Gravity.CENTER
            }

            val leftRes = a.getResourceId(R.styleable.HeaderView_leftIcon, 0)
            if (leftRes != 0) {
                binding.ivLeft.setImageResource(leftRes)
                binding.ivLeft.visibility = VISIBLE
            } else {
                binding.ivLeft.visibility = GONE
            }

            val rightRes = a.getResourceId(R.styleable.HeaderView_rightIcon, 0)
            if (rightRes != 0) {
                binding.ivRight.setImageResource(rightRes)
                binding.ivRight.visibility = VISIBLE
            } else {
                binding.ivRight.visibility = GONE
            }

            a.recycle()
        }

        enableMarquee()
    }

    val leftBtn = binding.ivLeft

    val rightBtn = binding.ivRight

    private fun enableMarquee() {
        binding.tvTitle.isSelected = true
    }

    fun setTitle(text: String) {
        binding.tvTitle.text = text
    }
}

