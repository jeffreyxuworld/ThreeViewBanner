package com.worldtech.threeviewbanner.activity;

import com.worldtech.threeviewbanner.R;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static final String[] URLS = {
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2860421298,3956393162&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=163638141,898531478&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1028426622,4209712325&fm=26&gp=0.jpg"
    };

    public static final int[] DRAWABLES = {
            R.drawable.growth_value_level_first_bg,
            R.drawable.growth_value_level_second_bg,
            R.drawable.growth_value_level_third_bg
    };

    public static List<String> getData(int size) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(URLS[i]);
        }
        return list;
    }


}
