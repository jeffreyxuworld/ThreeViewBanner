package com.worldtech.threeviewbanner.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldtech.threeviewbanner.R
import java.util.*

class TestAdapter internal constructor() : RecyclerView.Adapter<TestViewHolder>() {
    private val mColors: IntArray
    internal val mPosition: IntArray
    private val mItemsCount = 100
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        Log.e("!!!!!!!!!", "onCreateViewHolder")
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return TestViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        Log.e("!!!!!!!!!", "onBindViewHolder: $position")
        holder.tv_name.setBackgroundColor(mColors[position])
        holder.tv_name.text = "---->$position"
    }

    override fun getItemCount(): Int {
        return mItemsCount
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    init {
        mColors = IntArray(mItemsCount)
        mPosition = IntArray(mItemsCount)
        var i = 0
        while (mItemsCount > i) {
            val random = Random()
            mColors[i] =
                Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
            mPosition[i] = i
            ++i
        }
    }
}