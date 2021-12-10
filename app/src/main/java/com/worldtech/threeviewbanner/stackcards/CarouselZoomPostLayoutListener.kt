package com.worldtech.threeviewbanner.stackcards;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * Implementation of {@link CarouselLayoutManager.PostLayoutListener} that makes interesting scaling of items. <br />
 * We are trying to make items scaling quicker for closer items for center and slower for when they are far away.<br />
 * Tis implementation uses atan function for this purpose.
 */
public class CarouselZoomPostLayoutListener extends CarouselLayoutManager.PostLayoutListener {

    private final float mScaleMultiplier;

    public CarouselZoomPostLayoutListener() {
        this(0.1f);
    }

    public CarouselZoomPostLayoutListener(final float scaleMultiplier) {
        mScaleMultiplier = scaleMultiplier;
    }

    @Override
    public ItemTransformation transformChild(@NonNull final View child, final float itemPositionToCenterDiff, final int orientation) {

        final float scale = 1.0f - mScaleMultiplier * Math.abs(itemPositionToCenterDiff);
        Log.d("Lei","itemPositionToCenterDiff = " + itemPositionToCenterDiff + ",scale = " + scale);

        // because scaling will make view smaller in its center, then we should move this item to the top or bottom to make it visible
        final float translateY;
        final float translateX;
        if (CarouselLayoutManager.VERTICAL == orientation) {
            final float translateYGeneral = child.getMeasuredHeight() * (1 - scale) / 2f;
            translateY = Math.signum(itemPositionToCenterDiff) * translateYGeneral;
            translateX = 0;
        } else {
            final float translateXGeneral = child.getMeasuredWidth() * (1 - scale) / 2f;
            translateX = Math.signum(itemPositionToCenterDiff) * translateXGeneral;
            translateY = 0;
        }

        return new ItemTransformation(scale, scale, translateX, translateY);
    }
}