package com.worldtech.threeviewbanner.stackcards

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class DefaultChildSelectionListener protected constructor(
    private val mOnCenterItemClickListener: OnCenterItemClickListener,
    recyclerView: RecyclerView,
    carouselLayoutManager: CarouselLayoutManager
) : CarouselChildSelectionListener(recyclerView, carouselLayoutManager) {
    override fun onCenterItemClicked(
        recyclerView: RecyclerView,
        carouselLayoutManager: CarouselLayoutManager,
        v: View
    ) {
        mOnCenterItemClickListener.onCenterItemClicked(recyclerView, carouselLayoutManager, v)
    }

    override fun onBackItemClicked(
        recyclerView: RecyclerView,
        carouselLayoutManager: CarouselLayoutManager,
        v: View
    ) {
        recyclerView.smoothScrollToPosition(carouselLayoutManager.getPosition(v))
    }

    interface OnCenterItemClickListener {
        fun onCenterItemClicked(
            recyclerView: RecyclerView,
            carouselLayoutManager: CarouselLayoutManager,
            v: View
        )
    }

    companion object {
        fun initCenterItemListener(
            onCenterItemClickListener: OnCenterItemClickListener,
            recyclerView: RecyclerView,
            carouselLayoutManager: CarouselLayoutManager
        ): DefaultChildSelectionListener {
            return DefaultChildSelectionListener(
                onCenterItemClickListener,
                recyclerView,
                carouselLayoutManager
            )
        }
    }
}