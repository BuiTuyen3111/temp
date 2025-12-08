package com.zip.lock.screen.wallpapers.widget

import com.zip.lock.screen.wallpapers.R
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class RoundedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val path = Path()
    private val rect = RectF()
    private val radii = FloatArray(8)

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView)
        val radius = ta.getDimension(R.styleable.RoundedImageView_radius, 0f)

        val topLeft = ta.getDimension(R.styleable.RoundedImageView_radius_top_left, radius)
        val topRight = ta.getDimension(R.styleable.RoundedImageView_radius_top_right, radius)
        val bottomRight = ta.getDimension(R.styleable.RoundedImageView_radius_bottom_right, radius)
        val bottomLeft = ta.getDimension(R.styleable.RoundedImageView_radius_bottom_left, radius)
        ta.recycle()

        setCornerRadii(topLeft, topRight, bottomRight, bottomLeft)
    }

    fun setCornerRadii(tl: Float, tr: Float, br: Float, bl: Float) {
        val arr = floatArrayOf(tl, tl, tr, tr, br, br, bl, bl)
        System.arraycopy(arr, 0, radii, 0, 8)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        rect.set(0f, 0f, width.toFloat(), height.toFloat())
        path.reset()
        path.addRoundRect(rect, radii, Path.Direction.CW)
        canvas.clipPath(path)
        super.onDraw(canvas)
    }
}
