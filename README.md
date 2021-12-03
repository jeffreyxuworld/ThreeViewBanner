# ThreeViewBanner
[![Platform Android](https://img.shields.io/badge/platform-Android-brightgreen)](https://developer.android.com/)

**一屏三页 banner 控件，基于 ViewPager2 实现，并且带指示器。**

**banner 后面的背景图片可随着 banner 页面的滑动，背景图在临界点会变化图片以及有 alpha 值的渐变过程。**

**代码基于 Kotlin 语言编写**

ThreeViewBanner:一屏三页 banner 的主要实现逻辑；

BaseBanner：基础 banner 控件代码；

IndicatorView：banner 对应的指示器；

ScaleInTransformer：通过 CompositePageTransformer 为 ViewPager2 设置一个页面缩放的 ScaleInTransformer

-----------------------------------------------------------------
新版本新增：层叠卡片（3个卡片旋转木马效果）


**页面运行截图：**

![Demo页面录制](https://upload-images.jianshu.io/upload_images/633041-213f626705e5f8c2.gif?imageMogr2/auto-orient/strip)


