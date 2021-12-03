package com.worldtech.threeviewbanner.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.worldtech.threeviewbanner.R

class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tv_name: TextView = itemView.findViewById(R.id.tv_name)
}