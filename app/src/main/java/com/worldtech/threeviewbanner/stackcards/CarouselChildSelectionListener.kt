package com.worldtech.threeviewbanner.stackcards

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener

abstract class CarouselChildSelectionListener protected constructor(
    private val mRecyclerView: RecyclerView,
    private val mCarouselLayoutManager: CarouselLayoutManager
) {
    private val mOnClickListener = View.OnClickListener { v ->
        val holder = mRecyclerView.getChildViewHolder(v)
        val position = holder.adapterPosition
        if (position == mCarouselLayoutManager.getCenterItemPosition()) {
            onCenterItemClicked(mRecyclerView, mCarouselLayoutManager, v)
        } else {
            onBackItemClicked(mRecyclerView, mCarouselLayoutManager, v)
        }
    }

    protected abstract fun onCenterItemClicked(
        recyclerView: RecyclerView,
        carouselLayoutManager: CarouselLayoutManager,
        v: View
    )

    protected abstract fun onBackItemClicked(
        recyclerView: RecyclerView,
        carouselLayoutManager: CarouselLayoutManager,
        v: View
    )

    init {
        mRecyclerView.addOnChildAttachStateChangeListener(object :
            OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {
                view.setOnClickListener(mOnClickListener)
            }

            override fun onChildViewDetachedFromWindow(view: View) {
                view.setOnClickListener(null)
            }
        })
    }
}