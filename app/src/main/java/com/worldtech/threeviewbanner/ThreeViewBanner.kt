package com.worldtech.threeviewbanner

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import kotlin.math.abs

/**
 * 一屏三页 banner 控件
 */
class ThreeViewBanner @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseBanner(
    context!!, attrs, defStyleAttr
) {
    private var listener: OnPageChangeListener? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    fun setOnPageChangeListener(listener: OnPageChangeListener?) {
        this.listener = listener
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        mAdapter = adapter
        viewPager2?.adapter = adapter
        viewPager2?.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (listener != null) {
                    listener!!.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }
                if (indicator != null) {
                    indicator!!.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }
            }

            override fun onPageSelected(position: Int) {
                if (listener != null) {
                    listener!!.onPageSelected(position)
                }
                if (indicator != null) {
                    indicator!!.onPageSelected(position)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (indicator != null) {
                    indicator!!.onPageScrollStateChanged(state)
                }
            }
        })
        startPager(0)
    }

    fun startPager(startPosition: Int) {
        viewPager2!!.setCurrentItem(startPosition, true)
        if (indicator != null) {
            indicator!!.initIndicatorCount(realCount)
        }
    }

    /**
     * 设置一屏多页
     *
     * @param tlWidth    左边页面显露出来的宽度
     * @param brWidth    右边页面露出来的宽度
     * @param pageMargin item与item之间的宽度
     */
    fun setPageMargin(tlWidth: Int, brWidth: Int, pageMargin: Int): ThreeViewBanner {
        //ViewPager2的一屏多页可以通过为RecyclerView设置Padding来实现。
        var pageMargin = pageMargin
        if (pageMargin < 0) pageMargin = 0
        //通过CompositePageTransformer为ViewPager设置了MarginPageTransformer
        addPageTransformer(MarginPageTransformer(pageMargin))
        val recyclerView = viewPager2!!.getChildAt(0) as RecyclerView
        if (viewPager2!!.orientation == ViewPager2.ORIENTATION_VERTICAL) {
            recyclerView.setPadding(
                viewPager2!!.paddingLeft,
                tlWidth + abs(pageMargin),
                viewPager2!!.paddingRight,
                brWidth + abs(pageMargin)
            )
        } else {
            recyclerView.setPadding(
                tlWidth + abs(pageMargin),
                viewPager2!!.paddingTop,
                brWidth + abs(pageMargin),
                viewPager2!!.paddingBottom
            )
        }
        recyclerView.clipToPadding = false
        return this
    }

    val realCount: Int
        get() = mAdapter!!.itemCount

    interface OnPageChangeListener {
        fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
        fun onPageSelected(position: Int)
    }
}