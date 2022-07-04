package com.neppplus.gudocin_android.util

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

class CustomViewPager : ViewPager {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightSpec = heightMeasureSpec
        val mode = MeasureSpec.getMode(heightSpec)
        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            super.onMeasure(widthMeasureSpec, heightSpec)
            var height = 0
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                child.measure(
                    widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                )
                val h = child.measuredHeight
                if (h > height) height = h
            }
            heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, heightSpec)
    }
}