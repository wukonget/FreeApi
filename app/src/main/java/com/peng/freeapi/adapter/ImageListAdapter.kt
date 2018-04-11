package com.peng.freeapi.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.peng.freeapi.R
import com.peng.freeapi.activitys.PictureViewActivity
import com.peng.freeapi.model.ImageModel
import kotlinx.android.synthetic.main.imagelistitem.view.*
import java.util.*

class ImageListAdapter(activity: Activity, nameList: ArrayList<ImageModel> = ArrayList<ImageModel>()) : RecyclerView.Adapter<ImageListAdapter.ImageListHolder>() {

    private var mActivity = activity
    private var mImageList = nameList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListHolder {
        return ImageListHolder(View.inflate(mActivity, R.layout.imagelistitem, null))
    }

    override fun getItemCount(): Int {
        return mImageList.size
    }

    override fun onBindViewHolder(holder: ImageListHolder, position: Int) {

        holder.image.setOnClickListener {
            if(mImageList.size>position) {
                PictureViewActivity.launch(mActivity, mImageList, position)
            }
        }

        Glide.with(mActivity)
                .load(mImageList[position].url)
                .into(holder.image)


    }

    fun setData(imageList: ArrayList<ImageModel>?) {
        if (imageList === null) {
            mImageList = ArrayList<ImageModel>()
        } else {
            mImageList = imageList!!
        }
    }


    class ImageListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.pictureViewPager
    }
}