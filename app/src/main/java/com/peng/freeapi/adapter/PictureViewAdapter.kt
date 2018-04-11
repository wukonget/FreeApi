package com.peng.freeapi.adapter

import android.app.Activity
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.peng.freeapi.model.ImageModel


class PictureViewAdapter(activity:Activity, images : ArrayList<ImageModel> = ArrayList<ImageModel>()) : PagerAdapter() {

    val mActivity = activity
    var mImages = images

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return mImages.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val url = mImages[position].url
        val photoView = PhotoView(mActivity)
        Glide.with(mActivity)
                .load(url)
                .into(photoView)
        container.addView(photoView)
        photoView.setOnClickListener{
            mActivity.finish()
        }
        return photoView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}