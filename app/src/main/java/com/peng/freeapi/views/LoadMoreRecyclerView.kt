package com.peng.freeapi.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.GridLayoutManager
import com.peng.freeapi.utils.CommonUtil


/**
 *use:      支持加载更多的RecyclerView
 *author:    pengchong
 *time:      2018/4/12  15:21
 */

class LoadMoreRecyclerView : RecyclerView {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context,attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    var mLoadMoreListener : OnLoadMoreListener? = null

    override fun onScrollStateChanged(state: Int) {
        if (state === RecyclerView.SCROLL_STATE_IDLE) {
            val layoutManager = layoutManager
            val lastVisiblePosition = when (layoutManager) {
                is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
                is StaggeredGridLayoutManager -> {
                    val into = IntArray(layoutManager.spanCount)
                    layoutManager.findLastVisibleItemPositions(into)
                    CommonUtil.findMax(into)
                }
                else -> (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }

            if (layoutManager.childCount > 0             //当当前显示的item数量>0

                    && lastVisiblePosition >= layoutManager.itemCount - 1           //当当前屏幕最后一个加载项位置>=所有item的数量

                    && layoutManager.itemCount > layoutManager.childCount) { // 当当前总Item数大于可见Item数
                    mLoadMoreListener?.loadMore()
            }

        }

    }

    interface OnLoadMoreListener{
        fun loadMore()
    }

    fun setOnLoadMoreListener(loadMoreListener:OnLoadMoreListener){
        mLoadMoreListener = loadMoreListener
    }



}