package com.zip.lock.screen.wallpapers.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var x = 0f
    private val paint = Paint().apply { color = Color.RED }
    private val speed = 50f

    private val updateRunnable = object : Runnable {
        override fun run() {
            x += speed
            if (x > width) x = 0f
            invalidate() // yêu cầu vẽ lại
            postDelayed(this, 16) // ~60 FPS
        }
    }

    init {
        post(updateRunnable)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //canvas.drawColor(Color.WHITE)
        canvas.drawCircle(x, height / 2f, 50f, paint)
    }
}
