package com.zip.lock.screen.wallpapers.widget

import com.zip.lock.screen.wallpapers.R
import com.zip.lock.screen.wallpapers.ext.toPx
import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.core.content.withStyledAttributes

class UIView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {
    private var cornerRadius = 0f.toPx()

    init {
        setWillNotDraw(false)
        clipToOutline = true // ✅ Bật clip theo outline

        attrs?.let {
            context.withStyledAttributes(it, R.styleable.UIView, 0, 0) {
                val radius = getDimension(R.styleable.UIView_android_radius, 0f.toPx())
                if (radius > 0) {
                    cornerRadius = radius
                }
            }
        }

        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, cornerRadius)
            }
        }
    }
}