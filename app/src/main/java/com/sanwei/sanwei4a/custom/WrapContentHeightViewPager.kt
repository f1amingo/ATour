package com.sanwei.sanwei4a.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.min

class WrapContentHeightViewPager : PagingViewPager {

    private val TAG = javaClass.simpleName

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d(TAG, "WrapContentHeightViewPager.onMeasure")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val view = getChildAt(0)
        view?.measure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measureHeight(heightMeasureSpec, view))
    }

    private fun measureHeight(measureSpec: Int, view: View?): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else { // set the height from the base view if available
            if (view != null) {
                result = view.measuredHeight
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = min(result, specSize)
            }
        }
        return result
    }
}