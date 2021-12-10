package com.worldtech.threeviewbanner.stackcards

import kotlin.jvm.JvmOverloads
import com.worldtech.threeviewbanner.stackcards.CarouselLayoutManager.PostLayoutListener
import android.view.View
import android.util.Log
import kotlin.math.sign

/**
 * Implementation of [CarouselLayoutManager.PostLayoutListener] that makes interesting scaling of items. <br></br>
 * We are trying to make items scaling quicker for closer items for center and slower for when they are far away.<br></br>
 * Tis implementation uses atan function for this purpose.
 */
class CarouselZoomPostLayoutListener @JvmOverloads constructor(private val mScaleMultiplier: Float = 0.1f) :
    PostLayoutListener() {
    override fun transformChild(
        child: View,
        itemPositionToCenterDiff: Float,
        orientation: Int
    ): ItemTransformation {
        val scale = 1.0f - mScaleMultiplier * Math.abs(itemPositionToCenterDiff)
        Log.d("Lei", "itemPositionToCenterDiff = $itemPositionToCenterDiff,scale = $scale")

        // because scaling will make view smaller in its center, then we should move this item to the top or bottom to make it visible
        val translateY: Float
        val translateX: Float
        if (CarouselLayoutManager.VERTICAL == orientation) {
            val translateYGeneral = child.measuredHeight * (1 - scale) / 2f
            translateY = sign(itemPositionToCenterDiff) * translateYGeneral
            translateX = 0f
        } else {
            val translateXGeneral = child.measuredWidth * (1 - scale) / 2f
            translateX = sign(itemPositionToCenterDiff) * translateXGeneral
            translateY = 0f
        }
        return ItemTransformation(scale, scale, translateX, translateY)
    }
}