package com.worldtech.threeviewbanner.activity

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.worldtech.threeviewbanner.IndicatorView
import com.worldtech.threeviewbanner.R
import com.worldtech.threeviewbanner.ScaleInTransformer
import com.worldtech.threeviewbanner.ThreeViewBanner
import com.worldtech.threeviewbanner.adapter.BannerAdapter
import com.worldtech.threeviewbanner.adapter.TestAdapter
import com.worldtech.threeviewbanner.databinding.ActivityThreeViewBannerBinding
import com.worldtech.threeviewbanner.stackcards.CarouselLayoutManager
import com.worldtech.threeviewbanner.stackcards.CarouselZoomPostLayoutListener
import com.worldtech.threeviewbanner.stackcards.CenterScrollListener
import com.worldtech.threeviewbanner.stackcards.DefaultChildSelectionListener
import java.util.*

class ThreeViewBannerActivity : BaseActivity() {

    private lateinit var currentBinding: ActivityThreeViewBannerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three_view_banner)
        currentBinding = ActivityThreeViewBannerBinding.inflate(layoutInflater)
        setContentView(currentBinding.root)
        initSystemBar(R.color.transparent)

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
            .setIndicatorColor(resources.getColor(R.color.color_white_alpha))
            .setParams(params)
            .setIndicatorSelectorColor(Color.WHITE)
        currentBinding.banner.let {
            banner.setPageMargin(50, 50, 40)
                .setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
                .setPagerScrollDuration(800)
                .setIndicator(it, indicatorView)
                .addPageTransformer(ScaleInTransformer())  //如果不需要缩放效果，去掉此行代码
        }


        val imageAdapter = BannerAdapter(Utils.getData(3))
        banner.setAdapter(imageAdapter)

        banner.setOnPageChangeListener(object : ThreeViewBanner.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                when (position) {
                    0 -> {
                        currentBinding.ivBg.alpha = 1.0f
                        currentBinding.ivBg.setImageResource(R.drawable.growth_value_level_first_bg)
                    }
                    1 -> {
                        currentBinding.ivBg.alpha = 1.0f
                        currentBinding.ivBg.setImageResource(R.drawable.growth_value_level_second_bg)
                    }
                    2 -> {
                        currentBinding.ivBg.alpha = 1.0f
                        currentBinding.ivBg.setImageResource(R.drawable.growth_value_level_third_bg)
                    }
                }
                changeBgAlpha(positionOffset, position)
            }
            override fun onPageSelected(position: Int) {
            }
        })


        initStackCards()


    }

    private fun initStackCards() {
        val adapter = TestAdapter()
        initRecyclerView(
            currentBinding.listHorizontal,
            CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false),
            adapter
        )
    }

    private fun changeBgAlpha(positionOffset: Float, position: Int) {
        if (positionOffset <= 0.3 && positionOffset > 0) {
            val alpha = 1 - positionOffset
            currentBinding.ivBg.alpha = alpha
        } else if (positionOffset > 0.3) {
            currentBinding.ivBg.alpha = positionOffset
            currentBinding.ivBg.setImageResource(Utils.DRAWABLES[position + 1])
        }
    }

    private fun dip2px(dp: Float): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }


    private fun initRecyclerView(
        recyclerView: RecyclerView,
        layoutManager: CarouselLayoutManager,
        adapter: TestAdapter
    ) {
        // enable zoom effect. this line can be customized
        layoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())
        layoutManager.setMaxVisibleItems(1)
        recyclerView.layoutManager = layoutManager
        // we expect only fixed sized item for now
        recyclerView.setHasFixedSize(true)
        // sample adapter with random data
        recyclerView.adapter = adapter
        // enable center post scrolling
        recyclerView.addOnScrollListener(CenterScrollListener())
        // enable center post touching on item and item click listener
        DefaultChildSelectionListener.initCenterItemListener({ recyclerView, carouselLayoutManager, v ->
            val position = recyclerView.getChildLayoutPosition(v)
            val msg = String.format(Locale.US, "Item %1\$d was clicked", position)
            Toast.makeText(this@ThreeViewBannerActivity, msg, Toast.LENGTH_SHORT).show()
        }, recyclerView, layoutManager)
        layoutManager.addOnItemSelectionListener { adapterPosition ->
            if (CarouselLayoutManager.INVALID_POSITION !== adapterPosition) {
                val value = adapter.mPosition[adapterPosition]
                /*
                        adapter.mPosition[adapterPosition] = (value % 10) + (value / 10 + 1) * 10;
                        adapter.notifyItemChanged(adapterPosition);
    */
            }
        }
    }

}