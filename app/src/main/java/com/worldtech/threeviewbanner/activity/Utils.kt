package com.worldtech.threeviewbanner.activity

import com.worldtech.threeviewbanner.R
import java.util.*

object Utils {
    private val URLS = arrayOf(
        "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2860421298,3956393162&fm=26&gp=0.jpg",
        "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=163638141,898531478&fm=26&gp=0.jpg",
        "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1028426622,4209712325&fm=26&gp=0.jpg"
    )
    val DRAWABLES = intArrayOf(
        R.drawable.growth_value_level_first_bg,
        R.drawable.growth_value_level_second_bg,
        R.drawable.growth_value_level_third_bg
    )

    fun getData(size: Int): List<String> {
        val list: MutableList<String> = ArrayList()
        for (i in 0 until size) {
            list.add(URLS[i])
        }
        return list
    }
}