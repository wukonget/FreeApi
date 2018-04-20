package com.peng.freeapi.anim

import android.support.v4.view.ViewPager
import android.view.View
import com.vondear.rxtools.RxLogTool


/**
 *use:
 *author:    pengchong
 *time:      2018/4/18  16:20
 */

class ScaleAnimViewPager : ViewPager.PageTransformer {

    private val MIN_SCALE = 0.7f
    private val MAX_SCALE = 1f

    override fun transformPage(page: View, position: Float) {
        var ratio = 1f
        when(position){
           in -1f..0f -> {
               ratio = MAX_SCALE + position*(MAX_SCALE-MIN_SCALE)
           }
            in 0f..1f -> {
                ratio = MAX_SCALE - position*(MAX_SCALE-MIN_SCALE)
            }
            else -> {
                ratio = MIN_SCALE
            }
        }

        page.scaleX = ratio
        page.scaleY = ratio
    }
}