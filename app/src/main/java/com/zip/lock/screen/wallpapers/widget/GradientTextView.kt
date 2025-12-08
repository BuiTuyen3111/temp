package com.zip.lock.screen.wallpapers.widget

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class GradientTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    var startColor: Int = 0xFF0AC26A.toInt()
    var endColor: Int = 0xFF02ABFF.toInt()
    var vertical: Boolean = false

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) updateGradient()
    }

    private fun updateGradient() {
        val shader = if (vertical)
            LinearGradient(0f, 0f, 0f, height.toFloat(),
                startColor, endColor, Shader.TileMode.CLAMP)
        else
            LinearGradient(0f, 0f, width.toFloat(), 0f,
                startColor, endColor, Shader.TileMode.CLAMP)

        paint.shader = shader
        invalidate()
    }

    fun setGradient(start: Int, end: Int, isVertical: Boolean = false) {
        startColor = start
        endColor = end
        vertical = isVertical
        updateGradient()
    }
}
