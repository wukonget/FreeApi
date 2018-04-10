package com.peng.freeapi.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager


object CommonUtil {
    fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }
}