package com.demo.popupwindowdemo.util;

import android.util.DisplayMetrics;

public class UIUtil {

    private static DisplayMetrics mDisplayMetrics = new DisplayMetrics();

    /**
     * 获取手机密度:low, mid, high, x
     */
    public static float getDensity() {
        return mDisplayMetrics.density;
    }

    /**
     * 获取屏幕宽度
     */
    public int getScreenWidth() {
        return mDisplayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public int getScreenHeight() {
        return mDisplayMetrics.heightPixels;
    }

    /**
     * dip变像px	 *
     *
     * @param dpValue
     * @return
     */
    public static int dipToPixels(float dpValue) {
        final float scale = getDensity();
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 像素变dip
     *
     * @param pxValue
     * @return
     */
    public static int PixelsToDip(float pxValue) {
        final float scale = getDensity();
        return (int) (pxValue / scale + 0.5f);
    }
}
