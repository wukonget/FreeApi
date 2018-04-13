package com.peng.freeapi.model

import android.content.Context
import com.peng.freeapi.R
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration


/**
 *use:      基础的RecyclerView 的 ItemDecoration
 *author:    pengchong
 *time:      2018/4/13  15:39
 */

class SimpleDividerGrid(context:Context) : Y_DividerItemDecoration(context) {

    private val mContext = context

    override fun getDivider(itemPosition: Int): Y_Divider {
        val color = mContext.resources.getColor(android.R.color.transparent)
        val lineWidth = 2f
        val halfWidth = 1f
        return when(itemPosition%2) {
            0 -> Y_DividerBuilder()
                .setBottomSideLine(true, color, lineWidth, 0f, 0f)
                .setTopSideLine(true, color, lineWidth, 0f, 0f)
                .setLeftSideLine(true, color, lineWidth, 0f, 0f)
                .setRightSideLine(true, color, halfWidth, 0f, 0f)
                .create()
            else -> Y_DividerBuilder()
                    .setBottomSideLine(true, color, lineWidth, 0f, 0f)
                    .setTopSideLine(true, color, lineWidth, 0f, 0f)
                    .setLeftSideLine(true, color, halfWidth, 0f, 0f)
                    .setRightSideLine(true, color, lineWidth, 0f, 0f)
                    .create()
        }
    }
}