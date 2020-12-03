package com.worldtech.threeviewbanner

import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * 通过CompositePageTransformer为ViewPager设置一个页面缩放的ScaleInTransformer
 */
class ScaleInTransformer : ViewPager2.PageTransformer {
    private var mMinScale = 0.85f

    constructor() {}
    constructor(minScale: Float) {
        mMinScale = minScale
    }

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height
        view.pivotY = (pageHeight shr 1).toFloat()
        view.pivotX = (pageWidth shr 1).toFloat()
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.scaleX = mMinScale
            view.scaleY = mMinScale
            view.pivotX = pageWidth.toFloat()
        } else if (position <= 1) {
            // [-1,1]
            // Modify the default slide transition to shrink the page as well
            if (position < 0) {
                //1-2:1[0,-1] ;2-1:1[-1,0]
                val scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                view.pivotX =
                    pageWidth * (DEFAULT_CENTER + DEFAULT_CENTER * -position)
            } else {
                //1-2:2[1,0] ;2-1:2[0,1]
                val scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                view.pivotX = pageWidth * ((1 - position) * DEFAULT_CENTER)
            }
        } else {
            // (1,+Infinity]
            view.pivotX = 0f
            view.scaleX = mMinScale
            view.scaleY = mMinScale
        }
    }

    companion object {
        private const val DEFAULT_CENTER = 0.5f
    }
}