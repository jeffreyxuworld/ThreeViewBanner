package com.worldtech.threeviewbanner

import android.view.View
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager.OnPageChangeListener

/**
 * 可以实现该接口，自定义Indicator 可参考内置的[IndicatorView]
 */
interface Indicator : OnPageChangeListener {
    /**
     * 当数据初始化完成时调用
     *
     * @param pagerCount pager数量
     */
    fun initIndicatorCount(pagerCount: Int)

    /**
     * 返回一个View，添加到banner中
     */
    val view: View?

    /**
     * banner是一个RelativeLayout，设置banner在RelativeLayout中的位置，可以是任何地方
     */
    fun getParams(): RelativeLayout.LayoutParams
}