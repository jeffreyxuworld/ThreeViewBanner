package com.worldtech.threeviewbanner

import android.content.Context
import android.graphics.Outline
import android.os.Build
import android.util.AttributeSet
import android.view.*
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2

/**
 * banner控件base代码
 */
class BaseBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    var compositePageTransformer: CompositePageTransformer? = null

    /**
     * 获取ViewPager2
     */
    var viewPager2: ViewPager2? = null
    var indicator: Indicator? = null
    var pagerScrollDuration = DEFAULT_PAGER_DURATION
    var startX = 0f
    var startY = 0f
    var lastX = 0f
    var lastY = 0f
    var scaledTouchSlop: Int
    private fun initViews(context: Context) {
        viewPager2 = ViewPager2(context)
        viewPager2!!.layoutParams = ViewGroup.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        viewPager2!!.setPageTransformer(CompositePageTransformer().also {
            compositePageTransformer = it
        })
        setOffscreenPageLimit(1)
        addView(viewPager2)
    }

    /**
     * 触摸事件，拦截处理
     * @param ev
     * @return
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        if (action == MotionEvent.ACTION_DOWN) {
            lastX = ev.rawX
            startX = lastX
            lastY = ev.rawY
            startY = lastY
        } else if (action == MotionEvent.ACTION_MOVE) {
            lastX = ev.rawX
            lastY = ev.rawY
            if (viewPager2!!.isUserInputEnabled) {
                val distanceX = Math.abs(lastX - startX)
                val distanceY = Math.abs(lastY - startY)
                val disallowIntercept: Boolean
                disallowIntercept =
                    if (viewPager2!!.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                        distanceX > scaledTouchSlop && distanceX > distanceY
                    } else {
                        distanceY > scaledTouchSlop && distanceY > distanceX
                    }
                parent.requestDisallowInterceptTouchEvent(disallowIntercept)
            }
        } else if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            return Math.abs(lastX - startX) > scaledTouchSlop || Math.abs(lastY - startY) > scaledTouchSlop
        }
        return super.onInterceptTouchEvent(ev)
    }

    /**
     * 设置viewpager2的自定义动画，支持多个添加
     */
    fun addPageTransformer(transformer: ViewPager2.PageTransformer?): BaseBanner {
        compositePageTransformer!!.addTransformer(transformer!!)
        return this
    }

    fun setOffscreenPageLimit(limit: Int): BaseBanner {
        viewPager2!!.offscreenPageLimit = limit
        return this
    }

    /**
     * 设置viewpager2的切换时长
     */
    fun setPagerScrollDuration(pagerScrollDuration: Long): BaseBanner {
        this.pagerScrollDuration = pagerScrollDuration
        return this
    }

    /**
     * 设置轮播方向
     *
     * @param orientation Orientation.ORIENTATION_HORIZONTAL or default
     * Orientation.ORIENTATION_VERTICAL
     */
    fun setOrientation(@ViewPager2.Orientation orientation: Int): BaseBanner {
        viewPager2!!.orientation = orientation
        return this
    }

    fun addItemDecoration(decor: ItemDecoration): BaseBanner {
        viewPager2!!.addItemDecoration(decor)
        return this
    }

    fun addItemDecoration(decor: ItemDecoration, index: Int): BaseBanner {
        viewPager2!!.addItemDecoration(decor, index)
        return this
    }

    /**
     * 设置indicator
     */
    fun setIndicator(indicator: Indicator?): BaseBanner {
        return setIndicator(indicator, true)
    }

    /**
     * 设置indicator，支持在xml中创建
     *
     * @param attachToRoot true 添加到banner布局中
     */
    fun setIndicator(indicator: Indicator?, attachToRoot: Boolean): BaseBanner {
        if (this.indicator != null) {
            removeView(this.indicator!!.view)
        }
        if (indicator != null) {
            this.indicator = indicator
            if (attachToRoot) {
                addView(this.indicator!!.view, this.indicator!!.getParams())
            }
        }
        return this
    }

    /**
     * 设置indicator
     */
    fun setIndicator(rootView: ViewGroup, indicator: Indicator?): BaseBanner {
        return setIndicator(rootView, indicator, true)
    }

    /**
     * 设置indicator，支持在xml中创建
     *
     * @param attachToRoot true 添加到banner布局中
     */
    fun setIndicator(
        rootView: ViewGroup,
        indicator: Indicator?,
        attachToRoot: Boolean
    ): BaseBanner {
        if (this.indicator != null) {
            removeView(this.indicator!!.view)
        }
        if (indicator != null) {
            this.indicator = indicator
            if (attachToRoot) {
                rootView.addView(this.indicator!!.view, this.indicator!!.getParams())
            }
        }
        return this
    }

    /**
     * 设置banner圆角 api21以上
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun setRoundCorners(radius: Float): BaseBanner {
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, radius)
            }
        }
        clipToOutline = true
        return this
    }

    companion object {
        const val DEFAULT_PAGER_DURATION: Long = 800
    }

    init {
        scaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop shr 1
        initViews(context)
    }
}