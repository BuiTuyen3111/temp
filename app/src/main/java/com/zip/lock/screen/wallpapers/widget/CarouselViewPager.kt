package com.zip.lock.screen.wallpapers.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class CarouselViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var _viewPager: ViewPager2? = null
    val viewPager get() = _viewPager!!
    val currentItem get() = viewPager.currentItem

    init {
        _viewPager = ViewPager2(context)
        clipChildren = false
        clipToPadding = false

        viewPager.clipChildren = false
        viewPager.clipToPadding = false
        viewPager.offscreenPageLimit = 3

        // disable overscroll
        (viewPager.getChildAt(0) as RecyclerView).apply {
            overScrollMode = OVER_SCROLL_NEVER
            clipToPadding = false
            val side = (64 * resources.displayMetrics.density).toInt()
            setPadding(side, 0, side, 0)
        }

        addView(viewPager,
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        )

        setPageTransformer()
    }

    private fun setPageTransformer() {
        val transformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(0))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
                page.scaleX = 0.85f + r * 0.15f
                page.alpha = 0.5f + r * 0.5f
            }
        }
        viewPager.setPageTransformer(transformer)
    }

    fun <VH : RecyclerView.ViewHolder> setAdapter(adapter: RecyclerView.Adapter<VH>) {
        viewPager.adapter = adapter
    }

    fun registerOnPageChangeCallback(callback: ViewPager2.OnPageChangeCallback) {
        viewPager.registerOnPageChangeCallback(callback)
    }

    fun setCurrentItem(item: Int, smoothScroll: Boolean = true) {
        viewPager.setCurrentItem(item, smoothScroll)
    }
}
