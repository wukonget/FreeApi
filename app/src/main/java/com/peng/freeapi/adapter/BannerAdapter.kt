package com.peng.freeapi.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.peng.freeapi.R
import com.peng.freeapi.model.BannerModel
import com.vondear.rxtools.RxImageTool
import kotlinx.android.synthetic.main.banneritem.view.*


/**
 *use:
 *author:    pengchong
 *time:      2018/4/17  13:54
 */
class BannerAdapter : PagerAdapter, ViewPager.OnPageChangeListener {

    private var mDataList: MutableList<BannerModel>? = null
    var mContext: Context? = null
    var mViewPager: ViewPager? = null
    var mDotContainer: LinearLayout? = null
    var currentPosition = 1

    constructor(context: Context, viewPager: ViewPager, dotContainer: LinearLayout) {
        mContext = context
        mViewPager = viewPager
        mDotContainer = dotContainer
        mViewPager!!.addOnPageChangeListener(this)

    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return if (mDataList == null) 0 else mDataList!!.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = View.inflate(mContext, R.layout.banneritem, null)
        Glide.with(mContext!!).load(mDataList!![position].imagePath).into(view.iv_banner)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    fun setData(dataList: MutableList<BannerModel>) {
        mDataList = dataList
        if (dataList.size > 0) {
            refreshDots(dataList.size)
            mDataList!!.add(dataList[0])
            mDataList!!.add(0, dataList[dataList.size - 1])
        }
        notifyDataSetChanged()
        mViewPager!!.setCurrentItem(1, false)
    }

    /**
     * 添加指示圆点
     */
    private fun refreshDots(count: Int) {
        mDotContainer?.removeAllViews()
        val px = RxImageTool.dp2px(8f)
        val layoutParam = LinearLayout.LayoutParams(px, px)
        layoutParam.leftMargin = px/2
        layoutParam.rightMargin = px/2
        layoutParam.topMargin = px/2
        layoutParam.bottomMargin = px/2
        for (index in 1..count) {
            var dotView: View? = if (index == currentPosition) {
                View.inflate(mContext, R.layout.dot_red, null)
            } else {
                View.inflate(mContext, R.layout.dot_white, null)
            }
            dotView!!.layoutParams = layoutParam
            mDotContainer?.addView(dotView)

        }

    }

    override fun onPageScrollStateChanged(state: Int) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (currentPosition == mDataList?.size?.minus(1)) {
                mViewPager?.setCurrentItem(1, false)
            } else if (currentPosition == 0) {
                mViewPager?.setCurrentItem(mDataList!!.size.minus(2), false)
            }
            refreshDots(mDataList!!.size - 2)
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        currentPosition = position
    }

    override fun onPageSelected(position: Int) {
    }

    fun nextBanner() {
        mViewPager?.setCurrentItem(currentPosition + 1, true)
    }

}