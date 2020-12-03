package com.worldtech.threeviewbanner.activity

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager2.widget.ViewPager2
import com.worldtech.threeviewbanner.IndicatorView
import com.worldtech.threeviewbanner.R
import com.worldtech.threeviewbanner.ThreeViewBanner
import com.worldtech.threeviewbanner.adapter.BannerAdapter

class ThreeViewBannerActivity : BaseActivity() {

    private var iv_bg: ImageView? = null
    private var rl_banner: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three_view_banner)
        initSystemBar(R.color.transparent)
        iv_bg = findViewById(R.id.iv_bg)
        rl_banner = findViewById(R.id.rl_banner)
        val banner: ThreeViewBanner = findViewById(R.id.banner)
        val params = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        params.bottomMargin = dip2px(10f)
        params.leftMargin = dip2px(15f)

        //使用内置Indicator
        val indicatorView: IndicatorView = IndicatorView(this)
            .setIndicatorRatio(1f) //ratio，默认值是1 ，也就是说默认是圆点
            .setIndicatorRadius(4f) //radius 点的大小
            .setIndicatorSelectedRatio(1f)
            .setIndicatorSelectedRadius(4f)
            .setIndicatorSpacing(4f)
            .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_CIRCLE)
            .setIndicatorColor(getResources().getColor(R.color.color_white_alpha))
            .setParams(params)
            .setIndicatorSelectorColor(Color.WHITE)
        //https://kotlinlang.org/docs/reference/null-safety.html#safe-calls
        rl_banner?.let {
            banner.setPageMargin(50, 50, 40)
                .setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
                .setPagerScrollDuration(800)
                .setIndicator(it, indicatorView)
        }


        val imageAdapter = BannerAdapter(Utils.getData(3))
        banner.setAdapter(imageAdapter)

        banner.setOnPageChangeListener(object : ThreeViewBanner.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == 0) {
                    iv_bg!!.alpha = 1.0f
                    iv_bg!!.setImageResource(R.drawable.growth_value_level_first_bg)
                } else if (position == 1) {
                    iv_bg!!.alpha = 1.0f
                    iv_bg!!.setImageResource(R.drawable.growth_value_level_second_bg)
                } else if (position == 2) {
                    iv_bg!!.alpha = 1.0f
                    iv_bg!!.setImageResource(R.drawable.growth_value_level_third_bg)
                }
                changeBgAlpha(positionOffset, position)
            }
            override fun onPageSelected(position: Int) {
            }
        })
    }

    private fun changeBgAlpha(positionOffset: Float, position: Int) {
        if (positionOffset <= 0.3 && positionOffset > 0) {
            val alpha = 1 - positionOffset
            iv_bg!!.alpha = alpha
        } else if (positionOffset > 0.3) {
            iv_bg!!.alpha = positionOffset
            iv_bg!!.setImageResource(Utils.DRAWABLES.get(position + 1))
        }
    }

    private fun dip2px(dp: Float): Int {
        return (dp * getResources().getDisplayMetrics().density).toInt()
    }
}