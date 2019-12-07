package com.sanwei.sanwei4a.util

import android.content.Context
import android.util.TypedValue

/**
 * Created by Johnson on 2018/3/20.
 *
 */

/**
 * 常用单位转换的工具类
 */
object DensityUtils {

    /**
     * dp转px
     *
     * @param context
     * @return
     */
    fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.resources
                .displayMetrics).toInt()
    }

    /**
     * sp转px
     *
     * @param context
     * @return
     */
    fun sp2px(context: Context, spVal: Float): Int {
        //        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources()
        //                .getDisplayMetrics());
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spVal * fontScale + 0.5f).toInt()
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    fun px2dp(context: Context, pxVal: Float): Float {
        val scale = context.resources.displayMetrics.density
        return pxVal / scale
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    fun px2sp(context: Context, pxVal: Float): Float {
        return pxVal / context.resources.displayMetrics.scaledDensity
    }

    /**
     * 得到屏幕宽度
     *
     * @param context
     * @return
     */
    fun getDisplayWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 得到屏幕高度
     *
     * @param context
     * @return
     */
    fun getDisplayHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }
}
