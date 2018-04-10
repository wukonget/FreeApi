package com.peng.freeapi.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.peng.freeapi.R
import com.peng.freeapi.model.ImageModel
import com.peng.freeapi.utils.CommonUtil
import kotlinx.android.synthetic.main.imagelistitem.view.*
import java.util.*

class ImageListAdapter(context: Context, nameList: ArrayList<ImageModel> = ArrayList<ImageModel>()) : RecyclerView.Adapter<ImageListAdapter.ImageListHolder>() {

    private var mContext = context
    private var mImageList = nameList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListHolder {
        return ImageListHolder(View.inflate(mContext, R.layout.imagelistitem, null))
    }

    override fun getItemCount(): Int {
        return mImageList.size
    }

    override fun onBindViewHolder(holder: ImageListHolder, position: Int) {
//        val width = CommonUtil.getScreenWidth(mContext)/2-4
//        val height = (width*(1+ Math.random())).toInt()
//        var layoutParams = holder.image.layoutParams
//        layoutParams.width = width
//        layoutParams.height = height
//        holder.image.layoutParams = layoutParams
        Glide.with(mContext)
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
        var image = itemView.image
    }
}