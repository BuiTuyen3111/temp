package com.zip.lock.screen.wallpapers.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toColorInt

class DoubleBorderProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 0
    private var max = 100

    private var outerBorderWidth = 2f * resources.displayMetrics.density
    private var innerBorderWidth = 6.5f * resources.displayMetrics.density

    private val borderBlackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    private val borderOrangePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = "#FCB100".toColorInt()
        style = Paint.Style.FILL
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    color = "#444444".toColorInt()
        style = Paint.Style.FILL
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = "#FF7F00".toColorInt()
        style = Paint.Style.FILL
    }

    private val radius = 50f

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()

        val outerRect = RectF(0f, 0f, w, h)
        canvas.drawRoundRect(outerRect, radius, radius, borderOrangePaint)

        val inset1 = outerBorderWidth
        val innerRect = RectF(inset1, inset1, w - inset1, h - inset1)
        canvas.drawRoundRect(innerRect, radius, radius, borderBlackPaint)

        val inset2 = outerBorderWidth + innerBorderWidth
        val bgRect = RectF(inset2, inset2, w - inset2, h - inset2)
        canvas.drawRoundRect(bgRect, radius, radius, backgroundPaint)

        if (max > 0) {
            val progressWidth = (progress.toFloat() / max) * bgRect.width()
            val progressRect = RectF(inset2, inset2, inset2 + progressWidth, h - inset2)
            canvas.drawRoundRect(progressRect, radius, radius, progressPaint)
        }
    }

    fun setProgress(value: Int) {
        progress = value.coerceIn(0, max)
        invalidate()
    }

    fun setMax(value: Int) {
        max = if (value > 0) value else 100
        invalidate()
    }

    fun setOuterBorderWidth(dp: Float) {
        outerBorderWidth = dp * resources.displayMetrics.density
        invalidate()
    }

    fun setInnerBorderWidth(dp: Float) {
        innerBorderWidth = dp * resources.displayMetrics.density
        invalidate()
    }
}

