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

class SimpleDividerLinear(context:Context) : Y_DividerItemDecoration(context) {

    private val mContext = context

    override fun getDivider(itemPosition: Int): Y_Divider {
        return Y_DividerBuilder()
                .setBottomSideLine(true,mContext.resources.getColor(R.color.thirty_black),2f,0f,0f)
                .create()
    }
}